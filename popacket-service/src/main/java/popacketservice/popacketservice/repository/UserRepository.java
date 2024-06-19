package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email=:email or u.document=:document")
    boolean existsByEmailOrDocument(@Param("email") String email,@Param("document") String document);

    @Query("SELECT u FROM User u WHERE u.document=:document")
    User findByDocument(@Param("document") String document);
}
