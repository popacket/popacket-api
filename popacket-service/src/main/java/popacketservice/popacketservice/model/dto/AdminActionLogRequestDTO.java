package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminActionLogRequestDTO {
    private Long userId;  // User ID is nullable because it's not strictly required in the definition

    @NotBlank(message = "El tipo de acción no puede estar vacío")
    @Size(max = 255, message = "El tipo de acción no debe exceder los 255 caracteres")
    private String actionType;

    @NotBlank(message = "La descripción de la acción no puede estar vacía")
    private String actionDescription;

    private LocalDateTime actionTimestamp;  // Optional, auto-set by the database if null
}
