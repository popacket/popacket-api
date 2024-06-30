package popacketservice.popacketservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.service.ShipmentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
        shipmentRequestDTO.setOriginLocationId(1L);
        shipmentRequestDTO.setDestinationLocationId(2L);
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
}
