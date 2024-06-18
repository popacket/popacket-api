package popacketservice.popacketservice.service;

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


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

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

    @Test
    void testUpdateProfileUser_UserExists_UpdateName() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setDocument("12345");
        userRequestDTO.setName("NewName");

        User user = new User();
        user.setDocument("12345");

        when(userRepository.existsByEmailOrDocument(userRequestDTO.getEmail(), userRequestDTO.getDocument())).thenReturn(true);
        when(userRepository.findByDocument(userRequestDTO.getDocument())).thenReturn(user);
        when(userMapper.convertToDTO(any(User.class))).thenReturn(new UserResponseDTO());

        UserResponseDTO result = userService.updateProfileUser(userRequestDTO, "name");

        assertNotNull(result);
        verify(userRepository).save(user);
        assertEquals("NewName", user.getName());
    }

    @Test
    void testUpdateProfileUser_UserDoesNotExist() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setDocument("12345");

        when(userRepository.existsByEmailOrDocument(userRequestDTO.getEmail(), userRequestDTO.getDocument())).thenReturn(false);

        assertThrows(ConflictException.class, () -> {
            userService.updateProfileUser(userRequestDTO, "name");
        });

        verify(userRepository, never()).save(any(User.class));
    }

    // Additional tests for other update scenarios

    @Test
    void testUpdateUserLastName() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setDocument("12345");
        userRequestDTO.setLastName("NewLastName");

        User user = new User();
        user.setDocument("12345");

        when(userRepository.existsByEmailOrDocument(userRequestDTO.getEmail(), userRequestDTO.getDocument())).thenReturn(true);
        when(userRepository.findByDocument(userRequestDTO.getDocument())).thenReturn(user);
        when(userMapper.convertToDTO(any(User.class))).thenReturn(new UserResponseDTO());

        UserResponseDTO result = userService.updateProfileUser(userRequestDTO,"lastName");

        assertNotNull(result);
        verify(userRepository).save(user);
        assertEquals("NewLastName", user.getLastName());
    }

    @Test
    void testUpdateUserPhone() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setDocument("12345");
        userRequestDTO.setPhone("555-5555");

        User user = new User();
        user.setDocument("12345");
        when(userRepository.existsByEmailOrDocument(userRequestDTO.getEmail(), userRequestDTO.getDocument())).thenReturn(true);
        when(userRepository.findByDocument(userRequestDTO.getDocument())).thenReturn(user);
        when(userMapper.convertToDTO(any(User.class))).thenReturn(new UserResponseDTO());

        UserResponseDTO result = userService.updateProfileUser(userRequestDTO, "phone");

        assertNotNull(result);
        verify(userRepository).save(user);
        assertEquals("555-5555", user.getPhone());
    }

    @Test
    void testUpdateUserEmail() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setDocument("12345");
        userRequestDTO.setEmail("newemail@example.com");

        User user = new User();
        user.setDocument("12345");
        when(userRepository.existsByEmailOrDocument(userRequestDTO.getEmail(), userRequestDTO.getDocument())).thenReturn(true);
        when(userRepository.findByDocument(userRequestDTO.getDocument())).thenReturn(user);
        when(userMapper.convertToDTO(any(User.class))).thenReturn(new UserResponseDTO());

        UserResponseDTO result = userService.updateProfileUser(userRequestDTO,"email");

        assertNotNull(result);
        verify(userRepository).save(user);
        assertEquals("newemail@example.com", user.getEmail());
    }
}
