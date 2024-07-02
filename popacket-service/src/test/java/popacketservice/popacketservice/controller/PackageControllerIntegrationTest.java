package popacketservice.popacketservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class PackageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreatePackage() throws Exception {
        // GIVEN
        PackageRequestDTO packageRequestDTO = new PackageRequestDTO();
        packageRequestDTO.setSenderId(3L);
        packageRequestDTO.setRecipientId(4L);
        packageRequestDTO.setDescription("Books");
        packageRequestDTO.setStatus("en tránsito");
        packageRequestDTO.setOriginAddress("123 Calle Falsa, Lima");
        packageRequestDTO.setDestinationAddress("321 Calle Verdadera, Arequipa");
        packageRequestDTO.setPaymentType("tarjeta de crédito");
        packageRequestDTO.setWeight(new BigDecimal("2.5"));


        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/packages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(packageRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // Utility method to convert an object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}