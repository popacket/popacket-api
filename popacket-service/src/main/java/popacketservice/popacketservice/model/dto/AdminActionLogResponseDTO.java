package popacketservice.popacketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminActionLogResponseDTO {
    private Long id;
    private Long userId;
    private String actionType;
    private String actionDescription;
    private LocalDateTime actionTimestamp;
}
