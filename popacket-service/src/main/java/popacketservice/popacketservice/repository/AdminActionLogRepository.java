package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import popacketservice.popacketservice.model.entity.AdminActionLog;

public interface AdminActionLogRepository extends JpaRepository<AdminActionLog, Long> {
}
