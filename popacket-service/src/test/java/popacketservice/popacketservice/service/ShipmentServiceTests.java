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
import popacketservice.popacketservice.repository.ShippingRateRepository;
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
    private ShippingRateRepository shipmentRateRepository;

    @Mock
    private ShipmentMapper shipmentMapper;

    @InjectMocks
    private ShipmentService shipmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
}
