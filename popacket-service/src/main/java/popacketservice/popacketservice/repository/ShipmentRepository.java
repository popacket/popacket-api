package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.Shipment;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Query("SELECT s FROM Shipment s WHERE s.id=:id")
    public Optional<Shipment> getShipmentById(@Param("id") Long id);

    @Query("SELECT s.status,s.pickupDateTime, s.deliveryDateTime FROM Shipment s WHERE s.id=:id")
    public Optional<Shipment> getStatusShipmentById(@Param("id") Long id);

    @Query("SELECT s.status,s.pickupDateTime, s.deliveryDateTime FROM Shipment s WHERE s.id=:id")
    public Optional<Object[]> getStatusShipmentByIdOb(@Param("id") Long id);

    @Query("SELECT COUNT(s) > 0 FROM Shipment s WHERE s.packageEntity.id=:id")
    boolean ifExistsByPackageID(@Param("id") Long id);

    @Query("SELECT s.packageEntity FROM Shipment s WHERE s.id=:id")
    Optional<Package> getPackageById(@Param("id") Long id);

    @Query("SELECT s FROM Shipment s WHERE s.packageEntity.id=:packageId")
    Optional<Shipment> getShipmentByPackageId(@Param("packageId") Long id);

    @Query("SELECT s FROM Shipment s WHERE s.packageEntity.id=:packageId")
    Optional<Shipment> findShipmentByPackageId(@Param("packageId") Long packageId);

    @Query("SELECT s FROM Shipment s WHERE s.packageEntity.id = :packageId")
    Optional<Shipment> findByPackageId(@Param("packageId") Long packageId);
}
