package popacketservice.popacketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private Long packageId;
    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentTiming;
    private LocalDateTime paymentDate;
}
