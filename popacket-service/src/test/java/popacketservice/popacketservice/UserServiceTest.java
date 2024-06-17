package popacketservice.popacketservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.UserMapper;
import popacketservice.popacketservice.model.dto.UserRequestDTO;
import popacketservice.popacketservice.model.dto.UserResponseDTO;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.UserRepository;
import popacketservice.popacketservice.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_UserDoesNotExist() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setDocument("12345");

        User user = new User();
        user.setEmail("test@example.com");
        user.setDocument("12345");

        when(userRepository.existsByEmailOrDocument(userRequestDTO.getEmail(), userRequestDTO.getDocument())).thenReturn(false);
        when(userMapper.convertToEntity(any(UserRequestDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.convertToDTO(any(User.class))).thenReturn(new UserResponseDTO());

        UserResponseDTO result = userService.createUser(userRequestDTO);

        assertNotNull(result);
        verify(userRepository).save(user);
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setDocument("12345");

        when(userRepository.existsByEmailOrDocument(userRequestDTO.getEmail(), userRequestDTO.getDocument())).thenReturn(true);

        assertThrows(ConflictException.class, () -> {
            userService.createUser(userRequestDTO);
        });

        verify(userRepository, never()).save(any(User.class));
    }
}
