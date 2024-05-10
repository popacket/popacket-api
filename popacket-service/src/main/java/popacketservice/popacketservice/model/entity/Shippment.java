package popacketservice.popacketservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class Shippment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="status", nullable = false)
    private String status;
    @Column(name="date_shipping", nullable = false)
    private String dateShipping;
    @Column(name="time_shipping", nullable = false)
    private String timeShipping;
    @Column(name="description")
    private String description;
}
