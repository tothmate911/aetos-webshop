package com.aetos.webshop.product.repository;

import com.aetos.webshop.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
