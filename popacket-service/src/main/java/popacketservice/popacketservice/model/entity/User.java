package popacketservice.popacketservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="document", nullable = false, unique = true)
    private String document;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="last_name", nullable = false)
    private String last_name;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="pass", nullable = false)
    private String pass;
    @Column(name="phone", nullable = false)
    private String phone;
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="created_at")
    private LocalDate createAt;
}
