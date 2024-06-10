package popacketservice.popacketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageResponseDTO {
    private Long id;
    private Long senderId;
    private Long recipientId;
    private String description;
    private BigDecimal weight;
    private String status;
    private String paymentType;
    private String originAddress;
    private String destinationAddress;
    private LocalDate createdAt;
}
