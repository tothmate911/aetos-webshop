package com.aetos.webshop.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    private Long itemId;

    private String name;

    private String description;

    private String imageUrl;

    private String category;

    private BigDecimal price;

    private int quantityOnStock;

}
