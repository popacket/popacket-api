package popacketservice.popacketservice.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.*;
import popacketservice.popacketservice.repository.*;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

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

}
