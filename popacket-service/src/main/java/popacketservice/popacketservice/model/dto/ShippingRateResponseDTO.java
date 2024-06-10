package popacketservice.popacketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingRateResponseDTO {
    private Long id;
    private BigDecimal weightMin;
    private BigDecimal weightMax;
    private BigDecimal basePrice;
    private BigDecimal pricePerKilometer;
    private String serviceType;
    private String region;
}
