package com.aetos.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class Product {

    @Id
    @GeneratedValue
    private Long productId;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private String imageUrl;

    private String category;

    private BigDecimal price;

    private int quantityOnStock;

}
