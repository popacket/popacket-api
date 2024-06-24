package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import popacketservice.popacketservice.exception.ResourceNotFoundException;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;
import popacketservice.popacketservice.repository.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShipmentMapper shipmentMapper;

    @InjectMocks
    private ShipmentService shipmentService;

    private List<Shipment> shipmentList;
    private List<ShipmentResponseDTO> shipmentResponseDTOList;

    @BeforeEach
    void setUp() {
        // Initialize shipment list
        Shipment shipment1 = new Shipment();
        shipment1.setId(1L);
        Shipment shipment2 = new Shipment();
        shipment2.setId(2L);
        shipmentList = List.of(shipment1, shipment2);

        // Initialize shipmentResponseDTOList
        ShipmentResponseDTO shipmentDTO1 = new ShipmentResponseDTO();
        shipmentDTO1.setId(1L);
        ShipmentResponseDTO shipmentDTO2 = new ShipmentResponseDTO();
        shipmentDTO2.setId(2L);
        shipmentResponseDTOList = List.of(shipmentDTO1, shipmentDTO2);
    }

    @Test
    void testGetAllShipmentsBySenderIDSuccess() {
        // Mock the repository call
        when(shipmentRepository.findAllShipmentBySenderId(anyLong())).thenReturn(Optional.of(shipmentList));

        // Mock the mapper call
        when(shipmentMapper.convertToListDTO(shipmentList)).thenReturn(shipmentResponseDTOList);

        // Call the service method
        List<ShipmentResponseDTO> result = shipmentService.getAllShipmentsBySenderID(1L);

        // Verify the results
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(shipmentRepository, times(1)).findAllShipmentBySenderId(anyLong());
        verify(shipmentMapper, times(1)).convertToListDTO(shipmentList);
    }

    @Test
    void testGetAllShipmentsBySenderIDNotFound() {
        // Mock the repository call to return empty
        when(shipmentRepository.findAllShipmentBySenderId(anyLong())).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(ResourceNotFoundException.class, () -> {
            shipmentService.getAllShipmentsBySenderID(1L);
        });

        // Verify the repository call
        verify(shipmentRepository, times(1)).findAllShipmentBySenderId(anyLong());
        verify(shipmentMapper, times(0)).convertToListDTO(anyList());
    }
}

