package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    @NotNull(message = "El ID del paquete no puede estar vacío")
    private Long packageId;

    @NotNull(message = "El monto no puede estar vacío")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    private BigDecimal amount;

    @NotBlank(message = "El método de pago no puede estar vacío")
    @Size(max = 50, message = "El método de pago no debe exceder los 50 caracteres")
    private String paymentMethod;

    @NotBlank(message = "El estado del pago no puede estar vacío")
    @Size(max = 20, message = "El estado del pago no debe exceder los 20 caracteres")
    private String paymentStatus;

    private LocalDateTime paymentDate; // Optional, auto-set by the database if null
}
