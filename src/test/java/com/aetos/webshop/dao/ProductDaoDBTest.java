package com.aetos.webshop.dao;

import com.aetos.webshop.model.Product;
import com.aetos.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductDaoDBTest {

    private ProductDao productDao;

    @Autowired
    private ProductRepository productRepository;
    
    @BeforeEach
    void setUp() {
        productDao = new ProductDaoDB(productRepository);
    }

    @Test
    void saveOneSimple() {
        Product product = Product.builder()
                .name("new product")
                .description("new description")
                .category("new category")
                .price(new BigDecimal(100))
                .quantityOnStock(1)
                .build();

        productDao.storeProduct(product);
        assertThat(productDao.getAll()).hasSize(1);
    }

}