package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.*;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.repository.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTests {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShipmentMapper shipmentMapper;

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private DeliveryPersonRepository deliveryPersonRepository;

    @InjectMocks
    private ShipmentService shipmentService;

    private ShipmentRequestDTO shipmentRequestDTO;
    private Shipment shipment;
    private ShipmentResponseDTO shipmentResponseDTO;
    private Package pack;
    private Location originLocation;
    private Location destinationLocation;
    private DeliveryPerson deliveryPerson;

    @BeforeEach
    void setUp() {
        shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setPackageId(1L);
        shipmentRequestDTO.setOriginLocationId(1L);
        shipmentRequestDTO.setDestinationLocationId(2L);
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
    }

    @Test
    void testMakeShipment_Successful() {
        when(shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId())).thenReturn(false);
        when(locationRepository.findById(shipmentRequestDTO.getDestinationLocationId())).thenReturn(Optional.of(destinationLocation));
        when(locationRepository.findById(shipmentRequestDTO.getOriginLocationId())).thenReturn(Optional.of(originLocation));
        when(packageRepository.findById(shipmentRequestDTO.getPackageId())).thenReturn(Optional.of(pack));
        when(deliveryPersonRepository.findById(shipmentRequestDTO.getDeliveryPersonId())).thenReturn(Optional.of(deliveryPerson));
        when(shipmentMapper.convertToEntity(shipmentRequestDTO)).thenReturn(shipment);
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(shipment);
        when(shipmentMapper.convertToDTO(shipment)).thenReturn(shipmentResponseDTO);

        ShipmentResponseDTO result = shipmentService.makeShipment(shipmentRequestDTO);

        assertNotNull(result);
        assertEquals(shipmentResponseDTO, result);
        verify(shipmentRepository, times(1)).ifExistsByPackageID(shipmentRequestDTO.getPackageId());
        verify(locationRepository, times(1)).findById(shipmentRequestDTO.getDestinationLocationId());
        verify(locationRepository, times(1)).findById(shipmentRequestDTO.getOriginLocationId());
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
}
