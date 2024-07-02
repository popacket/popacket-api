package popacketservice.popacketservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.ShippingRate;

import java.math.BigDecimal;

@Repository
public interface ShippingRateRepository extends JpaRepository<ShippingRate,Long> {

    @Query("SELECT sr.basePrice FROM ShippingRate sr WHERE (:weight BETWEEN sr.weightMin AND sr.weightMax) AND sr.serviceType=:serviceType")
    public BigDecimal getBasePrice(@Param("weight") BigDecimal weight, @Param("serviceType") String serviceType);

    @Query("SELECT sr.pricePerKilometer FROM ShippingRate sr WHERE (:weight BETWEEN sr.weightMin AND sr.weightMax) AND sr.serviceType=:serviceType")
    public BigDecimal getPricePerKilometer(@Param("weight") BigDecimal weight, @Param("serviceType") String serviceType);

    @Query("SELECT sr FROM ShippingRate sr WHERE (:weight BETWEEN sr.weightMin AND sr.weightMax) AND sr.serviceType=:serviceType")
    ShippingRate findByServiceTypeAndWeight(String serviceType, BigDecimal weight);
}
