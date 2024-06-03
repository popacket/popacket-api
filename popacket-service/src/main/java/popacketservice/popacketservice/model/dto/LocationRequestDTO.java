package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequestDTO {
    @NotBlank(message = "El nombre de la ubicación no puede estar vacío")
    @Size(max = 100, message = "El nombre de la ubicación no debe exceder los 100 caracteres")
    private String name;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres")
    private String address;

    @NotBlank(message = "El tipo de ubicación no puede estar vacío")
    @Size(max = 20, message = "El tipo de ubicación no debe exceder los 20 caracteres")
    private String type;
}
