package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.repository.ShipmentRepository;
import popacketservice.popacketservice.repository.ShippingRateRepository;

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

}
