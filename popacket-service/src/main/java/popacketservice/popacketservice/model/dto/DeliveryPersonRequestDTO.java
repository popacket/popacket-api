package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.*;
        import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    private String name;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 20, message = "El teléfono no debe exceder los 20 caracteres")
    @Pattern(regexp = "^[+]?[0-9]{9,13}$", message = "El teléfono debe ser un número válido")
    private String phone;

    @NotBlank(message = "El tipo no puede estar vacío")
    @Size(max = 20, message = "El tipo no debe exceder los 20 caracteres")
    private String type;

}
