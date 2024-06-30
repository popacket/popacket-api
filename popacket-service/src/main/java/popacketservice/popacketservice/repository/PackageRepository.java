package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Repository;
>>>>>>> develop
import popacketservice.popacketservice.model.entity.Package;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

    @Query("SELECT p FROM Package p WHERE p.sender=:id")
    Optional<List<Package>> findAllByOrderBySenderId(@Param("id") Long senderId);

    List<Package> findBySenderId(Long senderId);

    List<Package> findByRecipientId(Long recipientId);

    @Query("SELECT p FROM Package p WHERE  p.sender.id=:senderId and p.status=:status")
    List<Package> findByIdAndStatus(@Param("senderId") Long senderId,@Param("status") String status);
}
