package com.bookhaven.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Reader {
    @Id
    private String username;
    private String password;
    private String role;
}