package popacketservice.popacketservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="amount", nullable = false)
    private Float amount;
    @Column(name="date_pay", nullable = false)
    private LocalDateTime datePay;
    @Column(name="pay_method", nullable = false)
    private String payMethod;
}
