package popacketservice.popacketservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.DeliveryPersonMapper;
import popacketservice.popacketservice.model.dto.DeliveryPersonRequestDTO;
import popacketservice.popacketservice.model.dto.DeliveryPersonResponseDTO;
import popacketservice.popacketservice.model.entity.DeliveryPerson;
import popacketservice.popacketservice.repository.DeliveryPersonRepository;

@ExtendWith(MockitoExtension.class)
class DeliveryPersonServiceTest {

    @Mock
    private DeliveryPersonRepository deliveryPersonRepository;

    @Mock
    private DeliveryPersonMapper deliveryPersonMapper;

    @InjectMocks
    private DeliveryPersonService deliveryPersonService;

    private DeliveryPersonRequestDTO deliveryPersonRequestDTO;
    private DeliveryPerson deliveryPerson;
    private DeliveryPersonResponseDTO deliveryPersonResponseDTO;

    @BeforeEach
    void setUp() {
        deliveryPersonRequestDTO = new DeliveryPersonRequestDTO(
                "John Doe",
                "+123456789",
                "Courier"
        );

        deliveryPerson = new DeliveryPerson();
        deliveryPerson.setId(1L);
        deliveryPerson.setName(deliveryPersonRequestDTO.getName());
        deliveryPerson.setPhone(deliveryPersonRequestDTO.getPhone());
        deliveryPerson.setType(deliveryPersonRequestDTO.getType());

        deliveryPersonResponseDTO = new DeliveryPersonResponseDTO(
                1L,
                deliveryPersonRequestDTO.getName(),
                deliveryPersonRequestDTO.getPhone(),
                deliveryPersonRequestDTO.getType()
        );
    }

    @Test
    void testRegisterDeliveryPerson_Success() {
        // Arrange
        when(deliveryPersonMapper.convertToEntity(deliveryPersonRequestDTO)).thenReturn(deliveryPerson);
        when(deliveryPersonRepository.findByIdDeliveryPerson(deliveryPerson.getId())).thenReturn(false);
        when(deliveryPersonRepository.save(any(DeliveryPerson.class))).thenReturn(deliveryPerson);
        when(deliveryPersonMapper.convertToDTO(deliveryPerson)).thenReturn(deliveryPersonResponseDTO);

        // Act
        DeliveryPersonResponseDTO result = deliveryPersonService.RegisterDeliveryPerson(deliveryPersonRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(deliveryPersonRequestDTO.getName(), result.getName());
        assertEquals(deliveryPersonRequestDTO.getPhone(), result.getPhone());
        assertEquals(deliveryPersonRequestDTO.getType(), result.getType());
    }

    @Test
    void testRegisterDeliveryPerson_Conflict() {
        // Arrange
        when(deliveryPersonMapper.convertToEntity(deliveryPersonRequestDTO)).thenReturn(deliveryPerson);
        when(deliveryPersonRepository.findByIdDeliveryPerson(deliveryPerson.getId())).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> {
            deliveryPersonService.RegisterDeliveryPerson(deliveryPersonRequestDTO);
        });
    }
}