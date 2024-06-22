package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRequestDTO {

    @NotNull(message = "El ID del paquete no puede estar vacío")
    private Long packageId;

    @NotNull(message = "El ID de la ubicación de origen no puede estar vacío")
    private Long originLocationId;

    @NotNull(message = "El ID de la ubicación de destino no puede estar vacío")
    private Long destinationLocationId;

    @NotBlank(message = "El estado del envío no puede estar vacío")
    @Size(max = 20, message = "El estado del envío no debe exceder los 20 caracteres")
    private String status;

    @NotNull(message = "La fecha y hora de recogida no pueden estar vacías")
    private LocalDateTime pickupDateTime;

    @NotNull(message = "La fecha y hora de entrega no pueden estar vacías")
    private LocalDateTime deliveryDateTime;

    @NotNull(message = "El ID del repartidor no puede estar vacío")
    private Long deliveryPersonId;

    @NotNull(message = "El ID del cotizador no puede estar vacío")
    private Long shippingRateId;
}
