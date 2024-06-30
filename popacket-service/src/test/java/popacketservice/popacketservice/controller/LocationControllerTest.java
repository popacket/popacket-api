package popacketservice.popacketservice.controller;

import popacketservice.popacketservice.model.dto.LocationRequestDTO;
import popacketservice.popacketservice.model.dto.LocationResponseDTO;
import popacketservice.popacketservice.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    public void testCreateLocation_Success() throws Exception {
        LocationRequestDTO requestDTO = new LocationRequestDTO("LocationName", "LocationAddress", "LocationType");
        LocationResponseDTO expectedResponse = new LocationResponseDTO(1L, "LocationName", "LocationAddress", "LocationType");

        // Mock del servicio para retornar la respuesta esperada
        when(locationService.createLocation(any(LocationRequestDTO.class))).thenReturn(expectedResponse);

        // Realiza la solicitud POST al endpoint y verifica la respuesta
        mockMvc.perform(post("/location/registre-location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"LocationName\", \"address\": \"LocationAddress\", \"type\": \"LocationType\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("LocationName"))
                .andExpect(jsonPath("$.address").value("LocationAddress"))
                .andExpect(jsonPath("$.type").value("LocationType"));
    }
}
