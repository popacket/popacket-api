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
    private String name;
    @NotBlank(message = "El apellido no puede ser vacio")
    private String last_name;
    @NotBlank(message = "El correo electronico no puede ser vacio")
    @Email
    private String email;
    @NotBlank(message = "La contraseña no puede ir vacia")
    private String pass;
    @NotBlank(message = "El telefono no puede ser vacio")
    private String phone;
    @NotBlank(message = "La direccion no puede ser vacio")
    private String address;
}
