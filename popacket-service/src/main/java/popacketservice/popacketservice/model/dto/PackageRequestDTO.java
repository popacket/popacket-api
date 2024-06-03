package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageRequestDTO {
    @NotNull(message = "El ID del remitente no puede estar vacío")
    private Long senderId;

    private Long recipientId; // Puede ser nulo

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
    private String description;

    @NotNull(message = "El peso no puede estar vacío")
    @DecimalMin(value = "0.01", message = "El peso debe ser mayor que cero")
    private BigDecimal weight;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no debe exceder los 20 caracteres")
    private String status;

    @NotBlank(message = "El tipo de pago no puede estar vacío")
    @Size(max = 20, message = "El tipo de pago no debe exceder los 20 caracteres")
    private String paymentType;

    @NotBlank(message = "La dirección de origen no puede estar vacía")
    @Size(max = 255, message = "La dirección de origen no debe exceder los 255 caracteres")
    private String originAddress;

    @NotBlank(message = "La dirección de destino no puede estar vacía")
    @Size(max = 255, message = "La dirección de destino no debe exceder los 255 caracteres")
    private String destinationAddress;
}
