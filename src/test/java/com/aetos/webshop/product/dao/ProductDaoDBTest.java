package com.aetos.webshop.product.dao;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class ProductDaoDBTest {

    private ProductDao productDao;

    private Product product1;
    private Product product2;
    private Product product3;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productDao = new ProductDaoDB(productRepository);

        product1 = Product.builder()
                .name("new product 1")
                .description("new description 1")
                .category("new category 1")
                .price(new BigDecimal(100))
                .quantityOnStock(1)
                .build();

        product2 = Product.builder()
                .name("new product 2")
                .description("new description 2")
                .category("new category 2")
                .price(new BigDecimal(200))
                .quantityOnStock(1)
                .build();

        product3 = Product.builder()
                .name("new product 3")
                .description("new description 3")
                .category("new category 3")
                .price(new BigDecimal(300))
                .quantityOnStock(1)
                .build();
    }

    @Test
    void saveOneSimple() {
        productDao.storeProduct(product1);
        assertThat(productDao.getAll()).hasSize(1);
    }

    @Test
    void getById() throws ProductNotFoundException {
        productDao.storeProduct(product1);
        productDao.storeProduct(product2);
        productDao.storeProduct(product3);

        assertThat(productDao.getById(product3.getProductId()).getProductId()).isNotEqualTo(null);
    }

    @Test
    void getNonExistingProduct() {
        assertThrows(ProductNotFoundException.class, () -> productDao.getById(10L));
    }

    @Test
    void updateProduct() throws ProductNotFoundException {
        productDao.storeProduct(product1);

        Product updatedProduct = Product.builder()
                .name("updated product")
                .description("new description 1")
                .category("new category 1")
                .price(new BigDecimal(100))
                .quantityOnStock(1)
                .build();
        productDao.updateItem(1L, updatedProduct);

        assertThat(productDao.getById(1L).getName()).isEqualTo("updated product");
    }

    @Test
    void updateNonExistingProduct() {
        assertThrows(ProductNotFoundException.class, () -> productDao.updateItem(5L, product2));
    }

    @Test
    void deleteProduct() throws ProductNotFoundException {
        productDao.storeProduct(product1);
        productDao.storeProduct(product2);
        productDao.storeProduct(product3);

        productDao.deleteItem(2L);
        assertThat(productDao.getAll()).hasSize(2);
        assertThrows(ProductNotFoundException.class, () -> productDao.getById(2L));
    }

    @Test
    void deleteNonExistingProduct() {
        assertThrows(ProductNotFoundException.class, () -> productDao.deleteItem(5L));
    }

}
