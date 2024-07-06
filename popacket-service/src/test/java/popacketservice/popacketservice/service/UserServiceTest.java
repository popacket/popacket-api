package popacketservice.popacketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.UserMapper;
import popacketservice.popacketservice.model.dto.LoginRequestDTO;
import popacketservice.popacketservice.model.dto.UserPreferencesDTO;
import popacketservice.popacketservice.model.dto.UserRequestDTO;
import popacketservice.popacketservice.model.dto.UserResponseDTO;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.UserRepository;


import java.util.NoSuchElementException;
import java.util.Optional;

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


        UserResponseDTO result = userService.updateProfileUser(userRequestDTO);
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

            userService.updateProfileUser(userRequestDTO);
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

        UserResponseDTO result = userService.updateProfileUser(userRequestDTO);

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

        UserResponseDTO result = userService.updateProfileUser(userRequestDTO);

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

        UserResponseDTO result = userService.updateProfileUser(userRequestDTO);

        assertNotNull(result);
        verify(userRepository).save(user);
        assertEquals("newemail@example.com", user.getEmail());
    }

    // Verifica que el inicio de sesión sea exitoso cuando se proporcionan datos correctos
    @Test
    void testLoginSuccess() {
        String email = "test@example.com";
        String password = "password";
        LoginRequestDTO loginRequest = new LoginRequestDTO(email, password);
        User userEntity = new User();
        userEntity.setEmail(email);
        userEntity.setPass(password);
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(userEntity);
        when(userMapper.convertToDTO(userEntity)).thenReturn(userResponse);
        UserResponseDTO result = userService.login(loginRequest);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository).findByEmail(email);
        verify(userMapper).convertToDTO(userEntity);
    }

    // Verifica el inicio de sesión cuando el usuario no existe
    @Test
    void testLoginUserNotFound() {

        String email = "unknown@example.com";
        String password = "password";
        LoginRequestDTO loginRequest = new LoginRequestDTO(email, password);

        when(userRepository.findByEmail(email)).thenReturn(null);

        ConflictException exception = assertThrows(ConflictException.class, () -> userService.login(loginRequest));

        assertEquals("El usuario no existe", exception.getMessage());
        verify(userRepository).findByEmail(email);
    }

    // Verifica que el inicio de sesión cuando la contraseña es incorrecta
    @Test
    void testLoginIncorrectPassword() {

        String email = "test@example.com";
        String correctPassword = "correctPassword";
        String incorrectPassword = "wrongPassword";
        LoginRequestDTO loginRequest = new LoginRequestDTO(email, incorrectPassword);
        User userEntity = new User();
        userEntity.setEmail(email);
        userEntity.setPass(correctPassword);

        when(userRepository.findByEmail(email)).thenReturn(userEntity);

        ConflictException exception = assertThrows(ConflictException.class, () -> userService.login(loginRequest));

        assertEquals("La contraseña es incorrecta", exception.getMessage());
        verify(userRepository).findByEmail(email);
    }

    //Cuando el usuario existe guarda la preferencias segun su Id
    @Test
    void updatePreferences_Successful() {
        // Given
        Long userId = 1L;
        UserPreferencesDTO preferencesDTO = new UserPreferencesDTO("123 Street", "Credit Card", "Standard");
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        userService.updatePreferences(userId, preferencesDTO);

        // Then
        verify(userRepository).save(user);
        assertEquals("123 Street", user.getDefaultShippingAddress());
        assertEquals("Credit Card", user.getPreferredPaymentMethod());
        assertEquals("Standard", user.getPreferredShippingType());
    }

    //Cuando el usuario NO existe y este lanza una exception
    @Test
    void updatePreferences_UserNotFound() {
        // Given
        Long userId = 1L;
        UserPreferencesDTO preferencesDTO = new UserPreferencesDTO("123 Street", "Credit Card", "Standard");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            userService.updatePreferences(userId, preferencesDTO);
        });
    }
}