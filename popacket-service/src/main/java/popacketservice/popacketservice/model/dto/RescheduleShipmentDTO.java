package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RescheduleShipmentDTO {
    @NotNull(message = "El ID del paquete no puede estar vacío")
    private Long packageId;

    @NotNull(message = "La fecha y hora de recogida no pueden estar vacías")
    private LocalDateTime pickupDateTime;

    @NotNull(message = "La fecha y hora de entrega no pueden estar vacías")
    private LocalDateTime deliveryDateTime;
}
