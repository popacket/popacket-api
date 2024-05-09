package popacketservice.popacketservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "weight", nullable = false)
    private double weight;
    @Column(name = "height", nullable = false)
    private double height;
    @Column(name = "width", nullable = false)
    private double length;
    @Column(name = "depth", nullable = false)
    private double depth;
}
