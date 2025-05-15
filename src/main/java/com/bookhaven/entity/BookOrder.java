package com.bookhaven.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class BookOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;
    private String shippingAddress;
    private Double total;

    @OneToOne
    private PaymentDetails paymentDetails;

    @ManyToOne
    private Reader reader;

    @ElementCollection
    private List<Long> literatureIds;
}