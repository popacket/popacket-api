package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;
import popacketservice.popacketservice.repository.ShipmentRateRepository;
import popacketservice.popacketservice.repository.ShipmentRepository;

import java.math.BigDecimal;
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
    void testGetShipmentCost_Satisfactory() {
        // Variables locales
        Double weight = 10.0;
        String serviceType = "standard";
        BigDecimal priceBase = BigDecimal.valueOf(20.0);
        BigDecimal pricePerKilometer = BigDecimal.valueOf(5.0);

        // Configurar los mocks
        when(shipmentRateRepository.getBasePrice(BigDecimal.valueOf(weight), serviceType)).thenReturn(priceBase);
        when(shipmentRateRepository.getPricePerKilometer(BigDecimal.valueOf(weight), serviceType)).thenReturn(pricePerKilometer);

        // Ejecutar el método
        Double cost = shipmentService.getShipmentCost(weight, serviceType);

        // Verificar el resultado
        assertEquals(70.0, cost);
    }

    @Test
    public void testGetShipmentCost_NoBasePrice() {
        // Arrange
        Double weight = 10.0;
        String serviceType = "Economico";
        BigDecimal priceBase = shipmentRateRepository.getBasePrice(BigDecimal.valueOf(weight), serviceType);
        BigDecimal pricePerKilometer = shipmentRateRepository.getPricePerKilometer(BigDecimal.valueOf(weight), serviceType);

        when(shipmentRateRepository.getBasePrice(any(BigDecimal.class), any(String.class))).thenReturn(null);
        when(shipmentRateRepository.getPricePerKilometer(any(BigDecimal.class), any(String.class))).thenReturn(pricePerKilometer);

        // Act
        Double cost = shipmentService.getShipmentCost(weight, serviceType);

        // Assert
        Double expectedCost = priceBase.add(pricePerKilometer.multiply(BigDecimal.valueOf(weight))).doubleValue();
        assertEquals(expectedCost, cost);
    }

    @Test
    public void testGetShipmentCost_NoPricePerKilometer() {
        // Arrange
        Double weight = 10.0;
        String serviceType = "Economico";
        BigDecimal basePrice = BigDecimal.valueOf(50);

        when(shipmentRateRepository.getBasePrice(any(BigDecimal.class), any(String.class))).thenReturn(basePrice);
        when(shipmentRateRepository.getPricePerKilometer(any(BigDecimal.class), any(String.class))).thenReturn(null);

        // Act
        Double cost = shipmentService.getShipmentCost(weight, serviceType);

        // Assert
        assertEquals(basePrice.doubleValue(), cost);
    }

    @Test
    public void testGetShipmentCost_NegativeWeight() {
        // Arrange
        Double weight = -5.0;
        String serviceType = "Economico";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> shipmentService.getShipmentCost(weight, serviceType));
    }

}
