package popacketservice.popacketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import popacketservice.popacketservice.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
<<<<<<< HEAD
=======

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email=:email or u.document=:document")
    boolean existsByEmailOrDocument(@Param("email") String email,@Param("document") String document);
>>>>>>> develop

    @Query("SELECT u FROM User u WHERE u.document=:document")
    User findByDocument(@Param("document") String document);

<<<<<<< HEAD
    boolean existsByEmailOrDocument(String email, String document);

    @Query("SELECT u FROM User u WHERE u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);
=======
    @Query("SELECT u FROM User u WHERE u.email=:email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email=:email")
    boolean existsByEmail(@Param("email") String email);
>>>>>>> develop
}
