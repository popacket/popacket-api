package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRequestDTO {

    @NotNull(message = "El ID del paquete no puede estar vacío")
    private Long packageId;

    @NotBlank(message = "La ubicación de origen no puede estar vacío")
    private Long originLocationAddress;

    @NotBlank(message = "La ubicación de destino no puede estar vacío")
    private Long destinationLocationAddress;

    @NotBlank(message = "El estado del envío no puede estar vacío")
    @Size(max = 20, message = "El estado del envío no debe exceder los 20 caracteres")
    private String status;

    @NotNull(message = "El ID del repartidor no puede estar vacío")
    private Long deliveryPersonId;

}
