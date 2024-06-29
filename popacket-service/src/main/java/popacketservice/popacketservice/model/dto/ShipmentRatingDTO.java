package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class ShipmentRatingDTO {
    @NotNull(message = "El ID del envío no puede ser nulo.")
    private Long shipmentId;

    @NotNull(message = "Se necesita calificación")
    @Min(value = 1, message = "La calificación debe ser al menos 1")
    @Max(value = 10, message = "La calificación no debe ser mayor a 10")
    private Integer rating;
    @Size(max = 255, message = "Los comentarios deben tener menos de 255 caracteres")
    private String comments;
}
