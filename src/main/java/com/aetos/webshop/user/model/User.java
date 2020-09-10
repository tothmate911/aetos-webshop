package com.aetos.webshop.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    Long userId;

    private String hashedPassword;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

}
