package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PackageRequestDTO {
    @NotNull(message="El peso no puede ser Vacio")
    private double weight;
    @NotNull(message="La altura no puede ser Vacia")
    private double height;
    @NotNull(message="El ancho no puede ser Vacio")
    private double width;
    @NotNull(message="La profundidad no puede ser Vacia")
    private double depth;
}
