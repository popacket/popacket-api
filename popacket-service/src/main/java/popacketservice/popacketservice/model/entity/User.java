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
    @Column(name="document", nullable = false, unique = true, length = 20)
    private String document;
    @Column(name="name", nullable = false, length = 100)
    private String name;
    @Column(name="last_name", nullable = false, length = 100)
    private String lastName;
    @Column(name="email", nullable = false, length = 100)
    private String email;
    @Column(name="pass" , nullable = false, length = 50)
    private String pass;
    @Column(name="phone", nullable = false, length = 20)
    private String phone;
    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin = false;
    @Column(name="created_at")
    private LocalDate createAt;
    @Column(name="default_shipping_address", nullable = true)
    private String defaultShippingAddress;
    @Column(name="preferred_payment_method", nullable = true)
    private String preferredPaymentMethod;
    @Column(name="preferred_shipping_type", nullable = true)
    private String preferredShippingType;

    @Column
    private String resetToken;
    private LocalDate tokenCreationDate;
}
