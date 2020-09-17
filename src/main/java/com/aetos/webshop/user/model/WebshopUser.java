package com.aetos.webshop.user.model;

import com.aetos.webshop.product.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebshopUser {

    @Id
    @GeneratedValue
    private Long userId;

    //email functions also as username
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
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
