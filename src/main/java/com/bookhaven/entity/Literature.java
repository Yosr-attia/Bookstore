package com.bookhaven.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Literature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private double price;
    private String category;
    private String imageUrl;
    @Column(length = 1000)
    private String description;
    private int quantity;
}
