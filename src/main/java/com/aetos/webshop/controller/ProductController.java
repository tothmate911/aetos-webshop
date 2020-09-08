package com.aetos.webshop.controller;

import com.aetos.webshop.dao.ProductDao;
import com.aetos.webshop.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private ProductDao productDao;

    @GetMapping("")
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @GetMapping("/{productId}")
    public Product getById(@PathVariable Long productId) {
        return productDao.getById(productId);
    }

    @PostMapping("")
    public Product storeItem(@RequestBody Product product) {
        return productDao.storeItem(product);
    }

    @PutMapping("/{productId}")
    public Product updateItem(@PathVariable Long productId, @RequestBody Product product) {
        return productDao.updateItem(productId, product);
    }

    @DeleteMapping("/{productId}")
    public Product deleteItem(@PathVariable Long productId) {
        return productDao.deleteItem(productId);
    }

}
