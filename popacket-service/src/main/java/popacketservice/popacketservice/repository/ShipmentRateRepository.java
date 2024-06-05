package popacketservice.popacketservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.ShippingRate;

import java.math.BigDecimal;

@Repository
public interface ShipmentRateRepository extends JpaRepository<ShippingRate,Long> {


    @Query("SELECT sr.basePrice FROM ShippingRate sr WHERE sr.weightMin>=:weight and sr.weightMax<=:weight")
    public Double getBasePrice(@Param("weight") BigDecimal weight);

    @Query("SELECT sr.pricePerKilometer FROM ShippingRate sr WHERE sr.weightMin>=:weight and sr.weightMax<=:weight")
    public Double getPricePerKilometer(@Param("weight") BigDecimal weight);
}
