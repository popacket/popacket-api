package popacketservice.popacketservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipments")

public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_shipment")
    private LocalDate date;
    @Column(name = "desciption")
    private String description;
    @Column(name = "status")
    private String status;
    @ManyToOne()
    @JoinColumn(name = "origin_address", nullable = false)
    private Address originAddress;
    @ManyToOne()
    @JoinColumn(name="destination_address", nullable = false)
    private Address destinationAddress;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne()
    @JoinColumn(name = "package_id", nullable = false)
    private Package aPackage;
}
