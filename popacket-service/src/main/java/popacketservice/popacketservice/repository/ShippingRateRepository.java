package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.ShippingRate;

import java.util.List;

@Repository
public interface ShippingRateRepository extends JpaRepository<ShippingRate, Long> {

    @Query("SELECT s.serviceType FROM ShippingRate s")
    List<String> getAllShippingRate();
}
