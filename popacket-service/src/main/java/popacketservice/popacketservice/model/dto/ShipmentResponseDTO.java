package popacketservice.popacketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponseDTO {
    private Long id;
    private Long packageId;
    private Long originLocationId;
    private Long destinationLocationId;
    private String status;
    private LocalDateTime pickupDateTime;
    private LocalDateTime deliveryDateTime;
    private Long deliveryPersonId;
    private Long shippingRateId;
}
