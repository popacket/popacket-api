package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "El numero de cuenta no puede esta vacio")
    @Size(min = 8, max = 11, message = "El numero de documento debe tener entre 5 a 20 caracteres")
    @Pattern(regexp = "[0-9]+", message = "El numero de documento debe contener solo digitos")
    private String document;
    @NotNull(message = "El nombre no puede ser vacio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String name;
    @NotBlank(message = "El apellido no puede ser vacio")
    @Size(max = 100, message = "El apellido no puede exceder los 100 caracteres")
    private String lastName;
    @NotBlank(message = "El correo electronico no puede ser vacio")
    @Email(message = "Debe ser una dirección de correo electrónico con formato correcto")
    @Size(max = 100, message = "El correo electrónico no puede exceder los 100 caracteres")
    private String email;
    @NotBlank(message = "La contraseña no puede ir vacia")
    @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
    private String pass;
    @NotBlank(message = "El telefono no puede ser vacio")
    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    @Pattern(regexp = "[0-9]+", message = "El numero de telefono debe contener solo digitos")
    private String phone;
}
