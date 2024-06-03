package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingRateRequestDTO {
    @NotNull(message = "El peso minimo puede estar vacío")
    @DecimalMin(value = "0.01", message = "El peso minimo debe ser mayor que cero")
    private BigDecimal weightMin;

    @NotNull(message = "El peso maximo no puede estar vacío")
    @DecimalMin(value = "0.01", message = "El peso maximo debe ser mayor que cero")
    private BigDecimal weightMax;

    @NotNull(message = "El precio base no puede estar vacío")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    private BigDecimal basePrice;

    @NotBlank(message = "El precio por kilo no puede estar vacío")
    @DecimalMin(value = "0.01", message = "El precio por kilo debe ser mayor que cero")
    private BigDecimal pricePerKilometer;

    @NotNull(message = "El tipo de servicio no puede estar vacío")
    private String serviceType;

    @NotNull(message = "La region no pueden estar vacío")
    private String region;
}
