package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentRequestDTO {
    private Long id;
    @NotBlank(message = "Descripcion no puede ser vacia")
    private String description;
    @NotBlank(message = "Estado no puede ser vacia")
    private String status;
    private String originAddressId;
    @NotBlank(message = "La direccion de destino no puede ser nula")
    private String destinationAddressId;
    @NotBlank(message = "El id del usuario no puede ser vacia")
    private String userId;
    @NotBlank(message = "El id del apquete no puede ser vacia")
    private String packageId;

}
