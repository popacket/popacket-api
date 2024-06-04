package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.AdminActionLog;
@Repository
public interface AdminActionLogRepository extends JpaRepository<AdminActionLog, Long> {
}
