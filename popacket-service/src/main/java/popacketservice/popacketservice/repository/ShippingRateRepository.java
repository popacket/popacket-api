package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.ShippingRate;

@Repository
public interface ShippingRateRepository extends JpaRepository<ShippingRate,Long> {
}
