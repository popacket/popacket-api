package popacketservice.popacketservice.service;

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
import popacketservice.popacketservice.service.DeliveryPersonService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class DeliveryPersonServiceTest {

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
        deliveryPersonRequestDTO = new DeliveryPersonRequestDTO();
        deliveryPersonRequestDTO.setName("John Doe");
        deliveryPersonRequestDTO.setPhone("123456789");

        deliveryPerson = new DeliveryPerson();
        deliveryPerson.setName("John Doe");
        deliveryPerson.setPhone("123456789");

        deliveryPersonResponseDTO = new DeliveryPersonResponseDTO();
        deliveryPersonResponseDTO.setName("John Doe");
        deliveryPersonResponseDTO.setPhone("123456789");
    }

    @Test
    void testRegisterDeliveryPerson_Successful() {
        when(deliveryPersonMapper.convertToEntity(deliveryPersonRequestDTO)).thenReturn(deliveryPerson);
        when(deliveryPersonRepository.existsByNameAndPhone(deliveryPerson.getName(), deliveryPerson.getPhone())).thenReturn(false);
        when(deliveryPersonRepository.save(any(DeliveryPerson.class))).thenReturn(deliveryPerson);
        when(deliveryPersonMapper.convertToDTO(deliveryPerson)).thenReturn(deliveryPersonResponseDTO);

        DeliveryPersonResponseDTO result = deliveryPersonService.registerDeliveryPerson(deliveryPersonRequestDTO);

        assertNotNull(result);
        assertEquals(deliveryPersonResponseDTO, result);
        verify(deliveryPersonRepository, times(1)).existsByNameAndPhone(deliveryPerson.getName(), deliveryPerson.getPhone());
        verify(deliveryPersonRepository, times(1)).save(deliveryPerson);
    }

    @Test
    void testRegisterDeliveryPerson_Conflict() {
        when(deliveryPersonMapper.convertToEntity(deliveryPersonRequestDTO)).thenReturn(deliveryPerson);
        when(deliveryPersonRepository.existsByNameAndPhone(deliveryPerson.getName(), deliveryPerson.getPhone())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class, () ->
                deliveryPersonService.registerDeliveryPerson(deliveryPersonRequestDTO));

        assertEquals("El Delivery Person Ya existe en el sistema", exception.getMessage());
        verify(deliveryPersonRepository, times(1)).existsByNameAndPhone(deliveryPerson.getName(), deliveryPerson.getPhone());
        verify(deliveryPersonRepository, never()).save(any(DeliveryPerson.class));
    }
}