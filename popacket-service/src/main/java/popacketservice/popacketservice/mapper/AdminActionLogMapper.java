package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.AdminActionLogRequestDTO;
import popacketservice.popacketservice.model.dto.AdminActionLogResponseDTO;
import popacketservice.popacketservice.model.entity.AdminActionLog;
import org.modelmapper.ModelMapper;

import java.util.List;

@Component
@AllArgsConstructor
public class AdminActionLogMapper {

    private final ModelMapper modelMapper;

    public AdminActionLog convertToEntity(AdminActionLogRequestDTO adminActionLogRequestDTO) {
        return modelMapper.map(adminActionLogRequestDTO, AdminActionLog.class);
    }

    public AdminActionLogResponseDTO convertToDTO(AdminActionLog adminActionLog) {
        return modelMapper.map(adminActionLog, AdminActionLogResponseDTO.class);
    }

    public List<AdminActionLogResponseDTO> convertToListDTO(List<AdminActionLog> adminActionLogs) {
        return adminActionLogs.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
