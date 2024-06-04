package popacketservice.popacketservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    @Query("SELECT p FROM Package p WHERE p.sender=:id")
    Optional<List<Package>> findAllByOrderBySenderId(@Param("id") Long senderId);
}
