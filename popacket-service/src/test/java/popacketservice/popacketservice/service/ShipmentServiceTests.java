package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.*;
import popacketservice.popacketservice.model.entity.*;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.repository.*;
import popacketservice.popacketservice.model.dto.RescheduleShipmentDTO;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;
import popacketservice.popacketservice.repository.ShipmentRepository;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ShipmentServiceTests {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShippingRateRepository shipmentRateRepository;

    @Mock
    private ShipmentMapper shipmentMapper;

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private DeliveryPersonRepository deliveryPersonRepository;

    private ShipmentRequestDTO shipmentRequestDTO;
    private Shipment shipment;
    private ShipmentResponseDTO shipmentResponseDTO;
    private Package pack;
    private Location originLocation;
    private Location destinationLocation;
    private DeliveryPerson deliveryPerson;

    @InjectMocks
    private ShipmentService shipmentService;

    @BeforeEach
    public void setUp() {

        shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setPackageId(1L);
        shipmentRequestDTO.setOriginLocationAddress("Origin Address");
        shipmentRequestDTO.setDestinationLocationAddress("Destination Address");
        shipmentRequestDTO.setDeliveryPersonId(1L);

        shipment = new Shipment();
        shipment.setId(1L);

        shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.setId(1L);

        pack = new Package();
        pack.setId(1L);

        originLocation = new Location();
        originLocation.setId(1L);

        destinationLocation = new Location();
        destinationLocation.setId(2L);

        deliveryPerson = new DeliveryPerson();
        deliveryPerson.setId(1L);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCancelShipmentById() {
        // Datos de prueba
        Long shipmentId = 1L;
        Shipment shipment = new Shipment();
        shipment.setId(shipmentId);
        shipment.setStatus("activo");

        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.setId(shipmentId);
        shipmentResponseDTO.setStatus("cancelado");

        // Simulación del comportamiento
        when(shipmentRepository.getShipmentById(shipmentId)).thenReturn(Optional.of(shipment));
        when(shipmentMapper.convertToDTO(any(Shipment.class))).thenReturn(shipmentResponseDTO);

        // Llamada al método a probar
        ShipmentResponseDTO result = shipmentService.cancelShipmentById(shipmentId);

        // Verificación
        assertEquals("cancelado", result.getStatus());
        verify(shipmentRepository, times(1)).save(any(Shipment.class));
    }

    @Test
    public void testCancelShipmentById_NotFound() {
        // Datos de prueba
        Long shipmentId = 1L;

        // Simulación del comportamiento
        when(shipmentRepository.getShipmentById(shipmentId)).thenReturn(Optional.empty());

        // Llamada al método a probar y verificación de la excepción
        assertThrows(RuntimeException.class, () -> shipmentService.cancelShipmentById(shipmentId));
    }

    @Test
    public void testGetShipmentCost() {
        // Datos de prueba
        Double weight = 10.0;
        String serviceType = "Economico";
        BigDecimal basePrice = BigDecimal.valueOf(10);
        BigDecimal pricePerKilometer = BigDecimal.valueOf(0.5);

        // Simulación del comportamiento
        when(shipmentRateRepository.getBasePrice(BigDecimal.valueOf(weight), serviceType)).thenReturn(basePrice);
        when(shipmentRateRepository.getPricePerKilometer(BigDecimal.valueOf(weight), serviceType)).thenReturn(pricePerKilometer);

        // Llamada al método a probar
        Double cost = shipmentService.getShipmentCost(weight, serviceType);

        // Verificación
        assertEquals(15.00, cost);
    }
    @Test
    void testMakeShipment_Successful() {
        when(shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId())).thenReturn(false);
        when(locationRepository.getLocationByAddress(shipmentRequestDTO.getDestinationLocationAddress())).thenReturn(Optional.of(destinationLocation));
        when(locationRepository.getLocationByAddress(shipmentRequestDTO.getOriginLocationAddress())).thenReturn(Optional.of(originLocation));
        when(packageRepository.findById(shipmentRequestDTO.getPackageId())).thenReturn(Optional.of(pack));
        when(deliveryPersonRepository.findById(shipmentRequestDTO.getDeliveryPersonId())).thenReturn(Optional.of(deliveryPerson));
        when(shipmentMapper.convertToEntity(shipmentRequestDTO)).thenReturn(shipment);
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(shipment);
        when(shipmentMapper.convertToDTO(shipment)).thenReturn(shipmentResponseDTO);

        ShipmentResponseDTO result = shipmentService.makeShipment(shipmentRequestDTO);

        assertNotNull(result);
        assertEquals(shipmentResponseDTO, result);
        verify(shipmentRepository, times(1)).ifExistsByPackageID(shipmentRequestDTO.getPackageId());
        verify(locationRepository, times(1)).getLocationByAddress(shipmentRequestDTO.getDestinationLocationAddress());
        verify(locationRepository, times(1)).getLocationByAddress(shipmentRequestDTO.getOriginLocationAddress());
        verify(packageRepository, times(1)).findById(shipmentRequestDTO.getPackageId());
        verify(deliveryPersonRepository, times(1)).findById(shipmentRequestDTO.getDeliveryPersonId());
        verify(shipmentRepository, times(1)).save(shipment);
    }

    @Test
    void testMakeShipment_Conflict() {
        when(shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class, () ->
                shipmentService.makeShipment(shipmentRequestDTO));

        assertEquals("El envio ya se encuentra registrado", exception.getMessage());
        verify(shipmentRepository, times(1)).ifExistsByPackageID(shipmentRequestDTO.getPackageId());
        verify(locationRepository, never()).findById(anyLong());
        verify(packageRepository, never()).findById(anyLong());
        verify(deliveryPersonRepository, never()).findById(anyLong());
        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    @Test
    public void getStatusShipmentById_Success() {

        Long shipmentId = 1L;
        Object[] expectedShipmentStatus = {"En camino", "2024-06-10"};

        when(shipmentRepository.getStatusShipmentByIdOb(anyLong())).thenReturn(Optional.of(expectedShipmentStatus));


        Object[] result = shipmentService.getStatusShipmentById(shipmentId);


        assertArrayEquals(expectedShipmentStatus, result);
        verify(shipmentRepository, times(1)).getStatusShipmentByIdOb(shipmentId);
    }

    @Test
    public void getStatusShipmentById_NotFound() {

        Long shipmentId = 1L;

        when(shipmentRepository.getStatusShipmentByIdOb(anyLong())).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.getStatusShipmentById(shipmentId);
        });

        assertEquals("El id del Envio no existe", exception.getMessage());
        verify(shipmentRepository, times(1)).getStatusShipmentByIdOb(shipmentId);
    }

    //Ecenario exitoso cuandos se encuentra todos los datos completdos exitosamente
    @Test
    void rateShipment_updatesRatingAndComments() {
        // Dado (Given)
        Long shipmentId = 1L;
        ShipmentRatingDTO ratingDto = new ShipmentRatingDTO(shipmentId, 10, "Excellent service");
        Shipment shipment = new Shipment();
        shipment.setId(shipmentId);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));

        // Cuando (When)
        shipmentService.rateShipment(ratingDto);

        // Entonces (Then)
        assertEquals(10, shipment.getRating());
        assertEquals("Excellent service", shipment.getComments());
        verify(shipmentRepository).save(shipment);
        verify(shipmentMapper).convertToDTO(shipment);
    }

    //Prueba de Envío No Encontrado
    @Test
    void rateShipment_throwsExceptionWhenShipmentNotFound() {

        Long shipmentId = 1L;
        ShipmentRatingDTO ratingDto = new ShipmentRatingDTO(shipmentId, 10, "Excelente servicio");

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.empty());


        assertThrows(NoSuchElementException.class, () -> {
            shipmentService.rateShipment(ratingDto);
        });

        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    //Calificacion fuera de rango de 1-10
    @Test
    void rateShipment_throwsExceptionWhenRatingIsOutOfRange() {
        Long shipmentId = 1L;
        ShipmentRatingDTO ratingDto = new ShipmentRatingDTO(shipmentId, 0, "Pesimo servicio"); // Calificación es 0, fuera de rango

        Shipment shipment = new Shipment();
        shipment.setId(shipmentId);
        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));
        assertThrows(IllegalArgumentException.class, () -> {
            shipmentService.rateShipment(ratingDto);
        });
        verify(shipmentRepository, never()).save(any(Shipment.class));
    }


    // Datos exitoso actualizada la reprogramacion
    @Test
    void updateScheduleShipment_updatesShipmentDates() {
        // Given las condiciones iniciales y preparamos los datos de prueba
        Long shipmentId = 1L;
        LocalDateTime newPickupDateTime = LocalDateTime.now().plusDays(1);
        LocalDateTime newDeliveryDateTime = newPickupDateTime.plusDays(3);
        RescheduleShipmentDTO rescheduleDTO = new RescheduleShipmentDTO(shipmentId, newPickupDateTime, newDeliveryDateTime);

        Shipment shipment = new Shipment();
        shipment.setId(shipmentId);
        shipment.setPickupDateTime(LocalDateTime.now());
        shipment.setDeliveryDateTime(LocalDateTime.now().plusDays(2));

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));

        // When
        shipmentService.rescheduleShipment(rescheduleDTO);

        // Then los resultados esperados
        verify(shipmentRepository).save(shipment);
        assertEquals(newPickupDateTime, shipment.getPickupDateTime(), "La fecha de recogida debe ser actualizada correctamente");
        assertEquals(newDeliveryDateTime, shipment.getDeliveryDateTime(), "La fecha de entrega debe ser actualizada correctamente");
    }

    // Envio no existe no se puede reprogramar
    @Test
    void updateScheduleShipment_throwsExceptionWhenShipmentNotFound() {
        // Given
        Long shipmentId = 1L;
        RescheduleShipmentDTO rescheduleDTO = new RescheduleShipmentDTO(shipmentId, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> shipmentService.rescheduleShipment(rescheduleDTO),
                "Debe lanzar una excepción cuando el envío no se encuentra.");
    }

    // Verifica las fechas no se crucen
    @Test
    void updateScheduleShipment_throwsExceptionWhenDeliveryBeforePickup() {
        // Given
        Long shipmentId = 1L;
        LocalDateTime newPickupDateTime = LocalDateTime.now();
        LocalDateTime newDeliveryDateTime = newPickupDateTime.minusDays(1); // Incorrecto
        RescheduleShipmentDTO rescheduleDTO = new RescheduleShipmentDTO(shipmentId, newPickupDateTime, newDeliveryDateTime);

        Shipment shipment = new Shipment();
        shipment.setId(shipmentId);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> shipmentService.rescheduleShipment(rescheduleDTO),
                "Debe lanzar una excepción cuando la fecha de entrega es anterior a la fecha de recogida.");
    }
}