package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.RescheduleShipmentDTO;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;
import popacketservice.popacketservice.repository.ShipmentRateRepository;
import popacketservice.popacketservice.repository.ShipmentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
