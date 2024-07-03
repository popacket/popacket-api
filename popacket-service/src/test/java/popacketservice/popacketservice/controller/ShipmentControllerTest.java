package popacketservice.popacketservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

    //Envio progrmado con exito
    @Test
    void updateScheduleShipment_success() throws Exception {
        ShipmentRequestDTO requestDto = new ShipmentRequestDTO();
        requestDto.setPackageId(1L);
        requestDto.setOriginLocationId(1L);
        requestDto.setDestinationLocationId(2L);
        requestDto.setStatus("scheduled");
        requestDto.setPickupDateTime(LocalDateTime.now().plusDays(1));
        requestDto.setDeliveryDateTime(requestDto.getPickupDateTime().plusDays(3));
        requestDto.setDeliveryPersonId(3L);
        requestDto.setShippingRateId(4L);

        ShipmentResponseDTO responseDto = new ShipmentResponseDTO(
                1L, 1L, 1L, 2L, "scheduled",
                requestDto.getPickupDateTime(), requestDto.getDeliveryDateTime(), 3L, 4L
        );

        when(shipmentService.updateScheduleShipment(any(ShipmentRequestDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/shipments/update-schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.status").value("scheduled"));
    }

    //Envio no programado lanza una exception con Id invalido
    @Test
    void updateScheduleShipment_notFound() throws Exception {
        ShipmentRequestDTO requestDto = new ShipmentRequestDTO();
        requestDto.setPackageId(999L); // Un ID inexistente
        requestDto.setOriginLocationId(1L);
        requestDto.setDestinationLocationId(2L);
        requestDto.setStatus("scheduled");
        requestDto.setPickupDateTime(LocalDateTime.now().plusDays(1));
        requestDto.setDeliveryDateTime(requestDto.getPickupDateTime().plusDays(3));
        requestDto.setDeliveryPersonId(3L);
        requestDto.setShippingRateId(4L);

        doThrow(new NoSuchElementException("Envío no encontrado con id: " + requestDto.getPackageId()))
                .when(shipmentService).updateScheduleShipment(any(ShipmentRequestDTO.class));

        mockMvc.perform(post("/shipments/update-schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound()); // Esperamos un estado 404 No encontrado
    }
}
