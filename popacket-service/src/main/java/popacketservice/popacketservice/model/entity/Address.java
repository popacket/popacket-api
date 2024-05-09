package popacketservice.popacketservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "addresses")

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name  = "address", nullable = false)
    private String address;
    @Column(name = "departament",nullable = false)
    private String departament;
    @Column(name = "province", nullable = false)
    private String province;
    @Column(name = "district", nullable = false)
    private String district;
    private double longitud;
    private double latitud;
}
