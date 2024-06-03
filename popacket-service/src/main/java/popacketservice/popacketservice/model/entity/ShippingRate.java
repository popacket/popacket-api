package popacketservice.popacketservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipping_rates")
public class ShippingRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight_min", nullable = false, precision = 10, scale = 2)
    private BigDecimal weightMin;

    @Column(name = "weight_max", nullable = false, precision = 10, scale = 2)
    private BigDecimal weightMax;

    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "price_per_kilometer", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerKilometer;

    @Column(name = "service_type", nullable = false, length = 50)
    private String serviceType;

    @Column(name = "region", length = 255)
    private String region;
}
