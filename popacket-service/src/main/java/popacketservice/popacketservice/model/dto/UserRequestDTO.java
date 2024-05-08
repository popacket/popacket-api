package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "El documento no puede estar vacio")
    @Size(min = 8, max = 11, message = "El numero de documento debe tener entre 8 a 11 caracteres")
    @Pattern(regexp = "[0-9]+", message = "El numero de documento debe contener solo digitos")
    private String document;
    @NotNull(message = "El nombre no puede ser vacio")
    private String name;
    @NotNull(message = "El apellido no puede ser vacio")
    private String last_name;
    @NotBlank(message = "El correo electronico no puede ser vacio")
    @Email
    private String email;
    @NotNull(message = "La contraseña no puede ser vacia")
    private String pass;
    @NotNull(message = "La telefono no puede ser vacio")
    private String phone;
    @NotNull(message = "La direccion no puede ser vacia")
    private String address;
}
