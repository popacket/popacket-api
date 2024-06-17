package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import popacketservice.popacketservice.model.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT COUNT(p) > 0 FROM Payment p WHERE p.packageEntity.id = :packageId")
    boolean existsByPackageId(@Param("packageId") Long packageId);
}
