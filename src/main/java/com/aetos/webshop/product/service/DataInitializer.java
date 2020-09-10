package com.aetos.webshop.product.service;

import com.aetos.webshop.product.dao.ProductDao;
import com.aetos.webshop.product.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
@Profile("production")
@AllArgsConstructor
public class DataInitializer {

    private ProductDao productDao;

    @PostConstruct
    public void init() {
        productDao.storeProduct(
                Product.builder()
                        .name("Lego Train station")
                        .description("A great lego")
                        .imageUrl("https://www.lego.com/cdn/cs/set/assets/blt3e446b6889283ae1/60050_alt1.jpg?fit=bounds&format=jpg&quality=80&width=320&height=320&dpr=1")
                        .category("Lego")
                        .price(new BigDecimal(13000))
                        .quantityOnStock(2)
                        .build()
        );

        productDao.storeProduct(
                Product.builder()
                        .name("Football")
                        .description("A great football")
                        .imageUrl("https://scoresports.com/media/catalog/product/cache/1/small_image/1710x1980/9df78eab33525d08d6e5fb8d27136e95/b/a/ball-wht.jpg")
                        .category("Sport")
                        .price(new BigDecimal(4000))
                        .quantityOnStock(1)
                        .build()
        );

        productDao.storeProduct(
                Product.builder()
                        .name("Lego")
                        .description("A great lego tractor")
                        .imageUrl("https://sportszert.hu/i.php?f=/images/products/lego_60181.jpg")
                        .category("Lego")
                        .price(new BigDecimal(20000))
                        .quantityOnStock(0)
                        .build()
        );
    }

}
