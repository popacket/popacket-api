package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippmentRequestDTO {
    @NotNull(message = "El estado no puede ser vacio")
    private String status;
    @NotBlank(message = "La fecha no puede ser vacia")
    private String dateShipping;
    @NotBlank(message = "La hora no puede ser vacia")
    private String timeShipping;
}
