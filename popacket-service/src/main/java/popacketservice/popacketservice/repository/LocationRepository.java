package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT COUNT(l) FROM Location l WHERE l.address=:address")
    boolean existsByAddress(@Param("address")String address);
    @Query("SELECT l FROM Location l WHERE l.address=:address")
    Location findByAddress(@Param("address")String address);

}
