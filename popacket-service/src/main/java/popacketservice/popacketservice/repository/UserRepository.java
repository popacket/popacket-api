package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import popacketservice.popacketservice.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
