package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;
import popacketservice.popacketservice.repository.ShipmentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShipmentMapper shipmentMapper;

    @InjectMocks
    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cancelShipmentById_Success() {
        // Arrange
        Long shipmentId = 1L;
        Shipment shipment = new Shipment();
        shipment.setId(shipmentId);
        shipment.setStatus("pendiente");

        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.setId(shipmentId);
        shipmentResponseDTO.setStatus("cancelado");

        when(shipmentRepository.getShipmentById(anyLong())).thenReturn(Optional.of(shipment));
        when(shipmentMapper.convertToDTO(any(Shipment.class))).thenReturn(shipmentResponseDTO);

        // Act
        ShipmentResponseDTO result = shipmentService.cancelShipmentById(shipmentId);

        // Assert
        assertEquals("cancelado", result.getStatus());
        verify(shipmentRepository, times(1)).getShipmentById(shipmentId);
        verify(shipmentRepository, times(1)).save(any(Shipment.class));
        verify(shipmentMapper, times(1)).convertToDTO(any(Shipment.class));
    }

    @Test
    void cancelShipmentById_NotFound() {
        // Arrange
        Long shipmentId = 1L;
        when(shipmentRepository.getShipmentById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.cancelShipmentById(shipmentId);
        });

        assertEquals("Shipment not found with id " + shipmentId, exception.getMessage());
        verify(shipmentRepository, times(1)).getShipmentById(shipmentId);
        verify(shipmentRepository, times(0)).save(any(Shipment.class));
        verify(shipmentMapper, times(0)).convertToDTO(any(Shipment.class));
    }
}