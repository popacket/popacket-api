package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddressRequestDTO {

    @NotBlank(message = "La direccion no puede estar vacia")
    private String address;
    @NotBlank(message = "El departamento no puede estar vacio")
    private String departament;
    @NotBlank(message = "La provincia no pued4e estar vacia")
    private String province;
    @NotBlank(message = "El distrito no puede estar vacio")
    private String district;

}
