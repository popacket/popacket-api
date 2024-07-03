package popacketservice.popacketservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import popacketservice.popacketservice.model.dto.RescheduleShipmentDTO;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.service.ShipmentService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ShipmentController.class)
public class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipmentService shipmentService;

    @Autowired
    private ObjectMapper objectMapper;

    // Envío reprogramado con éxito
    @Test
    void rescheduleShipment_success() throws Exception {
        RescheduleShipmentDTO rescheduleDto = new RescheduleShipmentDTO(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(4));

        ShipmentResponseDTO responseDto = new ShipmentResponseDTO(1L, 1L, 1L, 2L, "scheduled", rescheduleDto.getPickupDateTime(), rescheduleDto.getDeliveryDateTime(), 3L, 4L);

        when(shipmentService.rescheduleShipment(any(RescheduleShipmentDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/shipments/reschedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rescheduleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.status").value("scheduled"));
    }

    // Envío no encontrado, lanza excepción con ID inválido
    @Test
    void rescheduleShipment_notFound() throws Exception {
        RescheduleShipmentDTO rescheduleDto = new RescheduleShipmentDTO(999L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3)); // Un ID inexistente

        doThrow(new NoSuchElementException("Envío no encontrado con id: " + rescheduleDto.getPackageId()))
                .when(shipmentService).rescheduleShipment(any(RescheduleShipmentDTO.class));

        mockMvc.perform(post("/shipments/reschedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rescheduleDto)))
                .andExpect(status().isNotFound()); // Esperamos un estado 404 No encontrado
    }
}
