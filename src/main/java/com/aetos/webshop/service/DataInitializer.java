package com.aetos.webshop.service;

import com.aetos.webshop.dao.ItemDao;
import com.aetos.webshop.model.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class DataInitializer {

    private ItemDao itemDao;

    @PostConstruct
    public void init() {
        itemDao.storeItem(
                Item.builder()
                        .name("Lego Train station")
                        .description("A great lego")
                        .imageUrl("https://www.lego.com/cdn/cs/set/assets/blt3e446b6889283ae1/60050_alt1.jpg?fit=bounds&format=jpg&quality=80&width=320&height=320&dpr=1")
                        .category("Lego")
                        .price(new BigDecimal(13000))
                        .quantityOnStock(2)
                        .build()
        );

        itemDao.storeItem(
                Item.builder()
                        .name("Football")
                        .description("A great football")
                        .imageUrl("data:image/jpeg;base64,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")
                        .category("Sport")
                        .price(new BigDecimal(4000))
                        .quantityOnStock(1)
                        .build()
        );

        itemDao.storeItem(
                Item.builder()
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
