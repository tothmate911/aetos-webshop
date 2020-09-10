package com.aetos.webshop.user.model;

import com.aetos.webshop.product.model.Product;
import lombok.*;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String hashedPassword;

    private String firstName;
    private String lastName;

    @ElementCollection
    @CollectionTable(name = "cart",
            joinColumns = @JoinColumn(name = "product_id"))
    private Map<Product, Integer> Cart;

}
