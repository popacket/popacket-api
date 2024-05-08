package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import popacketservice.popacketservice.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Optional<User> findByUserDocument(String document);
}
