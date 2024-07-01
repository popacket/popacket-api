package popacketservice.popacketservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import popacketservice.popacketservice.model.dto.ShipmentRatingDTO;
import popacketservice.popacketservice.service.ShipmentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ShipmentController.class)
public class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipmentService shipmentService;

    //Cuando la validacion cumple todos los datos completos
    @Test
    void rateShipment_returnsSuccessMessageWithMaxRating() throws Exception {
        // Asumiendo que el servicio no devuelve un valor
        when(shipmentService.rateShipment(any(ShipmentRatingDTO.class))).thenReturn(null);

        // Usando la calificación máxima permitida que es 10
        mockMvc.perform(post("/shipments/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shipmentId\": 1, \"rating\": 10, \"comments\": \"Outstanding service\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Envío calificado exitosamente con una calificación de 10 y comentarios: Outstanding service"));
    }
}
