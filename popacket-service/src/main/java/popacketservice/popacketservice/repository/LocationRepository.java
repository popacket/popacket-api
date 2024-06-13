package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
