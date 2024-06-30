package popacketservice.popacketservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import popacketservice.popacketservice.model.dto.UserRequestDTO;
import popacketservice.popacketservice.model.dto.UserResponseDTO;
import popacketservice.popacketservice.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("testUser");
        userRequestDTO.setLastName("testLastName");
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setPass("password123");
        userRequestDTO.setPhone("1234567890");
        userRequestDTO.setDocument("71568432");

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setName("testUser");
        userResponseDTO.setLastName("testLastName");
        userResponseDTO.setEmail("test@example.com");
        userResponseDTO.setPhone("1234567890");
        userResponseDTO.setDocument("71568432");
    }

    @Test
    void testUpdateUserSuccess() throws Exception {
        when(userService.updateProfileUser(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

        mockMvc.perform(post("/users/configure_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(userResponseDTO)));
    }
    @Test
    void testUpdateUserValidationFailure() throws Exception {
        UserRequestDTO invalidUserRequestDTO = new UserRequestDTO();
        invalidUserRequestDTO.setName(""); // Invalid name
        invalidUserRequestDTO.setEmail("invalidEmail"); // Invalid email
        invalidUserRequestDTO.setPass("short"); // Invalid password
        invalidUserRequestDTO.setPhone("invalidPhone"); // Invalid phone
        invalidUserRequestDTO.setDocument(""); // Invalid document

        mockMvc.perform(post("/users/configure_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserRequestDTO)))
                .andExpect(status().isBadRequest());
    }
}
