package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PaymentRequestDTO {
    @NotNull()
    @Min(value= 0, message = "El precio no puede ser menor de 0.01")
    private BigDecimal price;
}
