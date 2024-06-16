package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.Package;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findBySenderId(Long senderId);
    List<Package> findByRecipientId(Long recipientId);
}
