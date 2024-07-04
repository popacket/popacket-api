package popacketservice.popacketservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import popacketservice.popacketservice.model.dto.RescheduleShipmentDTO;
import popacketservice.popacketservice.model.dto.ShipmentRatingDTO;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.service.ShipmentService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ShipmentController.class)
public class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipmentService shipmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetQuoteShipment() throws Exception {
        when(shipmentService.getShipmentCost(anyDouble(), anyString())).thenReturn(50.0);

        mockMvc.perform(get("/shipments/cost/10/serviceType")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cost").value(50.0));
    }

    @Test
    void testGetTrackingInfoById() throws Exception {
        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.setId(1L);

        when(shipmentService.getShipmentById(anyLong())).thenReturn(shipmentResponseDTO);

        mockMvc.perform(get("/shipments/tracking/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetTrackingOb() throws Exception {
        Object[] shipmentStatus = new Object[]{"In Transit", "2024-06-21"};

        when(shipmentService.getStatusShipmentById(anyLong())).thenReturn(shipmentStatus);

        mockMvc.perform(get("/shipments/tracking_2/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("In Transit"))
                .andExpect(jsonPath("$[1]").value("2024-06-21"));
    }

    @Test
    void testMakeShipment() throws Exception {
        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setPackageId(1L);
        shipmentRequestDTO.setOriginLocationAddress("origin addres");
        shipmentRequestDTO.setDestinationLocationAddress("Destination address");
        shipmentRequestDTO.setDeliveryPersonId(1L);

        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.setId(1L);

        when(shipmentService.makeShipment(any(ShipmentRequestDTO.class))).thenReturn(shipmentResponseDTO);

        mockMvc.perform(put("/shipments/makeShipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"packageId\": 1, \"originLocationId\": 1, \"destinationLocationId\": 2, \"deliveryPersonId\": 1 }"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCancelShipment() throws Exception {
        ShipmentResponseDTO shipmentResponseDTO = new ShipmentResponseDTO();
        shipmentResponseDTO.setId(1L);

        when(shipmentService.cancelShipmentById(anyLong())).thenReturn(shipmentResponseDTO);

        mockMvc.perform(post("/shipments/cancel/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

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

//    @Test
//    void rescheduleShipment_success() throws Exception {
//        RescheduleShipmentDTO rescheduleDto = new RescheduleShipmentDTO(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(4));
//
//        ShipmentResponseDTO responseDto = new ShipmentResponseDTO(1L, 1L, 1L, 2L, "scheduled", rescheduleDto.getPickupDateTime(), rescheduleDto.getDeliveryDateTime(), 3L, 4L);
//
//        when(shipmentService.rescheduleShipment(any(RescheduleShipmentDTO.class))).thenReturn(responseDto);
//
//        mockMvc.perform(post("/shipments/reschedule")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(rescheduleDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(responseDto.getId()))
//                .andExpect(jsonPath("$.status").value("scheduled"));
//    }

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