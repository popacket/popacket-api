package popacketservice.popacketservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import popacketservice.popacketservice.repository.ShipmentRepository;
import popacketservice.popacketservice.service.ShipmentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStatusShipmentById_Success() {

        Long shipmentId = 1L;
        Object[] expectedShipmentStatus = {"En camino", "2024-06-10"};

        when(shipmentRepository.getStatusShipmentByIdOb(anyLong())).thenReturn(Optional.of(expectedShipmentStatus));


        Object[] result = shipmentService.getStatusShipmentById(shipmentId);


        assertArrayEquals(expectedShipmentStatus, result);
        verify(shipmentRepository, times(1)).getStatusShipmentByIdOb(shipmentId);
    }

    @Test
    void getStatusShipmentById_NotFound() {

        Long shipmentId = 1L;

        when(shipmentRepository.getStatusShipmentByIdOb(anyLong())).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.getStatusShipmentById(shipmentId);
        });

        assertEquals("El id del Envio no existe", exception.getMessage());
        verify(shipmentRepository, times(1)).getStatusShipmentByIdOb(shipmentId);
    }
}