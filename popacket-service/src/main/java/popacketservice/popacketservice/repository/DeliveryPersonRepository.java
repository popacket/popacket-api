package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.DeliveryPerson;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {

    @Query("SELECT COUNT(d) > 0 FROM DeliveryPerson d WHERE d.id=:id")
    boolean findByIdDeliveryPerson(@Param("id") Long id);

    @Query("SELECT COUNT(d) > 0 FROM DeliveryPerson d WHERE d.name=:name and d.phone=:phone")
    boolean existsByNameAndPhone(@Param("name") String name,@Param("phone") String phone);
}
