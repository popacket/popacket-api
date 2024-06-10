package popacketservice.popacketservice.model.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonResponseDTO {
    private Long id;
    private String name;
    private String phone;
    private String type;
    private Long locationId;
}
