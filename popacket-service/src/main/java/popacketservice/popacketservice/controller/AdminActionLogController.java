package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popacketservice.popacketservice.model.dto.AdminActionLogRequestDTO;
import popacketservice.popacketservice.model.dto.AdminActionLogResponseDTO;
import popacketservice.popacketservice.service.AdminActionLogService;

import java.util.List;

@RestController
@RequestMapping("/admin-action-logs")
@AllArgsConstructor
public class AdminActionLogController {

    private final AdminActionLogService adminActionLogService;

    @PostMapping
    public ResponseEntity<AdminActionLogResponseDTO> createAdminActionLog(@Validated @RequestBody AdminActionLogRequestDTO adminActionLogDTO) {
        AdminActionLogResponseDTO adminActionLog = adminActionLogService.createAdminActionLog(adminActionLogDTO);
        return new ResponseEntity<>(adminActionLog, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AdminActionLogResponseDTO>> getAllAdminActionLogs() {
        List<AdminActionLogResponseDTO> adminActionLogs = adminActionLogService.getAllAdminActionLogs();
        return new ResponseEntity<>(adminActionLogs, HttpStatus.OK);
    }
}
