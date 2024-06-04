package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.Shipment;

import java.util.List;
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
