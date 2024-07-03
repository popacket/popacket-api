package popacketservice.popacketservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import popacketservice.popacketservice.model.dto.UserPreferencesDTO;
import popacketservice.popacketservice.service.UserService;
import popacketservice.popacketservice.model.entity.User;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setDefaultShippingAddress("123 Test St");
        user.setPreferredPaymentMethod("Credit Card");
        user.setPreferredShippingType("Express");
    }

    //Guarda las preferencias de usuario cumpliendo los campos
    @Test
    void updatePreferences_successful() throws Exception {
        UserPreferencesDTO preferencesDto = new UserPreferencesDTO("123 Test St", "Credit Card", "Express");

        when(userService.updatePreferences(anyLong(), any(UserPreferencesDTO.class))).thenReturn(user);

        mockMvc.perform(put("/users/preferences/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preferencesDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Preferencias actualizadas correctamente."));
    }

    //No cumple con los campos y lanza una excepcion

    @Test
    void updatePreferences_userNotFound() throws Exception {
        UserPreferencesDTO preferencesDto = new UserPreferencesDTO("123 Test St", "Credit Card", "Express");

        when(userService.updatePreferences(anyLong(), any(UserPreferencesDTO.class)))
                .thenThrow(new NoSuchElementException("Usuario no encontrado con ID: " + 1L));

        mockMvc.perform(put("/users/preferences/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preferencesDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
