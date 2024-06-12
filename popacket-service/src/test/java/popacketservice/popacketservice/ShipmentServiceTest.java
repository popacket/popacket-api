package popacketservice.popacketservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.*;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.repository.*;

import popacketservice.popacketservice.service.ShipmentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest {


    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private DeliveryPersonRepository deliveryPersonRepository;

    @Mock
    private ShipmentMapper shipmentMapper;


    @InjectMocks
    private ShipmentService shipmentService;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMakeShipment_Success() {
        // Given
        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setDestinationLocationId(1L);
        shipmentRequestDTO.setOriginLocationId(2L);
        shipmentRequestDTO.setPackageId(3L);
        shipmentRequestDTO.setDeliveryPersonId(4L);

        Location destinationLocation = new Location();
        Location originLocation = new Location();
        Package pack = new Package();
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        Shipment shipment = new Shipment();
        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();

        // When
        when(shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId())).thenReturn(false);
        when(locationRepository.findById(shipmentRequestDTO.getDestinationLocationId())).thenReturn(Optional.of(destinationLocation));
        when(locationRepository.findById(shipmentRequestDTO.getOriginLocationId())).thenReturn(Optional.of(originLocation));
        when(packageRepository.findById(shipmentRequestDTO.getPackageId())).thenReturn(Optional.of(pack));
        when(deliveryPersonRepository.findById(shipmentRequestDTO.getDeliveryPersonId())).thenReturn(Optional.of(deliveryPerson));
        when(shipmentMapper.convertToEntity(shipmentRequestDTO)).thenReturn(shipment);
        when(shipmentRepository.save(shipment)).thenReturn(shipment);
        when(shipmentMapper.convertToDTO(shipment)).thenReturn(shipmentResponseDTO);

        // Then
        ShipmentResponseDTO result = shipmentService.makeShipment(shipmentRequestDTO);

        assertNotNull(result);
        verify(shipmentRepository).save(shipment);
    }

    @Test
    public void testMakeShipment_Conflict() {
        // Given
        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setPackageId(3L);

        // When
        when(shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId())).thenReturn(true);

        // Then
        ConflictException exception = assertThrows(ConflictException.class, () -> {
            shipmentService.makeShipment(shipmentRequestDTO);
        });

        assertEquals("El envio ya se encuentra registrado", exception.getMessage());
        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    @Test
    public void testMakeShipment_DestinationLocationNotFound() {
        // Given
        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setDestinationLocationId(1L);

        // When
        when(shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId())).thenReturn(false);
        when(locationRepository.findById(shipmentRequestDTO.getDestinationLocationId())).thenReturn(Optional.empty());

        // Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.makeShipment(shipmentRequestDTO);
        });

        assertEquals("Destino no encontrado", exception.getMessage());
        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    @Test
    public void testMakeShipment_OriginLocationNotFound() {
        // Given
        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        Location destinationLocation = new Location();
        shipmentRequestDTO.setOriginLocationId(45L);

        // When
        when(shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId())).thenReturn(false);
        when(locationRepository.findById(shipmentRequestDTO.getDestinationLocationId())).thenReturn(Optional.of(destinationLocation));
        when(locationRepository.findById(shipmentRequestDTO.getOriginLocationId())).thenReturn(Optional.empty());

        // Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.makeShipment(shipmentRequestDTO);
        });

        assertEquals("Origen no encontrado", exception.getMessage());
        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    @Test
    public void testMakeShipment_PackageNotFound() {
        // Given
        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        Location destinationLocation = new Location();
        Location originLocation = new Location();
        shipmentRequestDTO.setPackageId(3L);

        // When
        when(shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId())).thenReturn(false);
        when(locationRepository.findById(shipmentRequestDTO.getDestinationLocationId())).thenReturn(Optional.of(destinationLocation));
        when(locationRepository.findById(shipmentRequestDTO.getOriginLocationId())).thenReturn(Optional.of(originLocation));
        when(packageRepository.findById(shipmentRequestDTO.getPackageId())).thenReturn(Optional.empty());

        // Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.makeShipment(shipmentRequestDTO);
        });

        assertEquals("Paquete no encontrado", exception.getMessage());
        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    @Test
    public void testMakeShipment_DeliveryPersonNotFound() {
        // Given
        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        Location destinationLocation = new Location();
        Location originLocation = new Location();
        Package pack = new Package();
        shipmentRequestDTO.setDeliveryPersonId(4L);

        // When
        when(shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId())).thenReturn(false);
        when(locationRepository.findById(shipmentRequestDTO.getDestinationLocationId())).thenReturn(Optional.of(destinationLocation));
        when(locationRepository.findById(shipmentRequestDTO.getOriginLocationId())).thenReturn(Optional.of(originLocation));
        when(packageRepository.findById(shipmentRequestDTO.getPackageId())).thenReturn(Optional.of(pack));
        when(deliveryPersonRepository.findById(shipmentRequestDTO.getDeliveryPersonId())).thenReturn(Optional.empty());

        // Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.makeShipment(shipmentRequestDTO);
        });

        assertEquals("Persona de entrega no encontrada", exception.getMessage());
        verify(shipmentRepository, never()).save(any(Shipment.class));
    }
}
    public void getStatusShipmentById_Success() {

        Long shipmentId = 1L;
        Object[] expectedShipmentStatus = {"En camino", "2024-06-10"};

        when(shipmentRepository.getStatusShipmentByIdOb(anyLong())).thenReturn(Optional.of(expectedShipmentStatus));


        Object[] result = shipmentService.getStatusShipmentById(shipmentId);


        assertArrayEquals(expectedShipmentStatus, result);
        verify(shipmentRepository, times(1)).getStatusShipmentByIdOb(shipmentId);
    }

    @Test
    public void getStatusShipmentById_NotFound() {

        Long shipmentId = 1L;

        when(shipmentRepository.getStatusShipmentByIdOb(anyLong())).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.getStatusShipmentById(shipmentId);
        });

        assertEquals("El id del Envio no existe", exception.getMessage());
        verify(shipmentRepository, times(1)).getStatusShipmentByIdOb(shipmentId);
    }
}

