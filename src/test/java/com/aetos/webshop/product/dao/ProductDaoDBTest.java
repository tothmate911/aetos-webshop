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
                .name("Lego Train station")
                .description("A great lego")
                .imageUrl("https://www.lego.com/cdn/cs/set/assets/blt3e446b6889283ae1/60050_alt1.jpg?fit=bounds&format=jpg&quality=80&width=320&height=320&dpr=1")
                .category("Lego")
                .price(new BigDecimal(13000))
                .quantityOnStock(2)
                .build();

        product2 = Product.builder()
                .name("Football")
                .description("A great football")
                .imageUrl("https://scoresports.com/media/catalog/product/cache/1/small_image/1710x1980/9df78eab33525d08d6e5fb8d27136e95/b/a/ball-wht.jpg")
                .category("Sport")
                .price(new BigDecimal(4000))
                .quantityOnStock(1)
                .build();

        product3 = Product.builder()
                .name("Lego")
                .description("A great lego tractor")
                .imageUrl("https://sportszert.hu/i.php?f=/images/products/lego_60181.jpg")
                .category("Lego")
                .price(new BigDecimal(20000))
                .quantityOnStock(0)
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

        assertThat(productDao.getById(product3.getProductId()).getProductId()).isNotNull();
    }

    @Test
    void getNonExistingProduct() {
        assertThrows(ProductNotFoundException.class, () -> productDao.getById(100L));
    }

    @Test
    void updateProduct() throws ProductNotFoundException {
        productDao.storeProduct(product1);
        Long productId = product1.getProductId();

        Product updatedProduct = Product.builder()
                .name("updated product")
                .description("new description 1")
                .imageUrl("\"https://www.lego.com/cdn/cs/set/assets/blt3e446b6889283ae1/60050_alt1.jpg?fit=bounds&format=jpg&quality=80&width=320&height=320&dpr=1\"")
                .category("new category 1")
                .price(new BigDecimal(100))
                .quantityOnStock(1)
                .build();
        productDao.updateItem(productId, updatedProduct);

        assertThat(productDao.getById(productId).getName()).isEqualTo("updated product");
    }

    @Test
    void updateNonExistingProduct() {
        assertThrows(ProductNotFoundException.class, () -> productDao.updateItem(500L, product2));
    }

    @Test
    void deleteProduct() throws ProductNotFoundException {
        productDao.storeProduct(product1);
        productDao.storeProduct(product2);
        productDao.storeProduct(product3);

        Long productId = product3.getProductId();

        productDao.deleteItem(productId);
        assertThat(productDao.getAll()).hasSize(2);
        assertThrows(ProductNotFoundException.class, () -> productDao.getById(productId));
    }

    @Test
    void deleteNonExistingProduct() {
        assertThrows(ProductNotFoundException.class, () -> productDao.deleteItem(4000L));
    }

}
