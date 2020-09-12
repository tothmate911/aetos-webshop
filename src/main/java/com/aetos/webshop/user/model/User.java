package com.aetos.webshop.user.model;

import com.aetos.webshop.product.model.Product;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="\"user\"")
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
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "quantity")
    @MapKeyJoinColumn(name = "product_id")
    @Builder.Default
    private Map<Product, Integer> cart = new HashMap<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

}
