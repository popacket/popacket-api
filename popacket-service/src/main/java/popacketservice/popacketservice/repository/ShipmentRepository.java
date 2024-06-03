package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import popacketservice.popacketservice.model.entity.Shipment;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
