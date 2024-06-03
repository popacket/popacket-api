package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.mapper.AdminActionLogMapper;
import popacketservice.popacketservice.model.dto.AdminActionLogRequestDTO;
import popacketservice.popacketservice.model.dto.AdminActionLogResponseDTO;
import popacketservice.popacketservice.model.entity.AdminActionLog;
import popacketservice.popacketservice.repository.AdminActionLogRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminActionLogService {

    private final AdminActionLogRepository adminActionLogRepository;
    private final AdminActionLogMapper adminActionLogMapper;

    public AdminActionLogResponseDTO createAdminActionLog(AdminActionLogRequestDTO adminActionLogRequestDTO) {
        AdminActionLog adminActionLog = adminActionLogMapper.convertToEntity(adminActionLogRequestDTO);
        adminActionLog = adminActionLogRepository.save(adminActionLog);
        return adminActionLogMapper.convertToDTO(adminActionLog);
    }

    public List<AdminActionLogResponseDTO> getAllAdminActionLogs() {
        List<AdminActionLog> adminActionLogs = adminActionLogRepository.findAll();
        return adminActionLogMapper.convertToListDTO(adminActionLogs);
    }
}
