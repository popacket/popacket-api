package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentRatingDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;
import popacketservice.popacketservice.repository.ShipmentRateRepository;
import popacketservice.popacketservice.repository.ShipmentRepository;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ShipmentServiceTests {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShipmentRateRepository shipmentRateRepository;

    @Mock
    private ShipmentMapper shipmentMapper;

    @InjectMocks
    private ShipmentService shipmentService;

    @BeforeEach
    public void setUp() {
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
        BigDecimal basePrice = BigDecimal.valueOf(50);
        BigDecimal pricePerKilometer = BigDecimal.valueOf(5);

        // Simulación del comportamiento
        when(shipmentRateRepository.getBasePrice(BigDecimal.valueOf(weight), serviceType)).thenReturn(basePrice);
        when(shipmentRateRepository.getPricePerKilometer(BigDecimal.valueOf(weight), serviceType)).thenReturn(pricePerKilometer);

        // Llamada al método a probar
        Double cost = shipmentService.getShipmentCost(weight, serviceType);

        // Verificación
        assertEquals(55.0, cost);
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

}
