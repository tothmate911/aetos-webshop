package com.aetos.webshop.controller;

import com.aetos.webshop.dao.ProductDao;
import com.aetos.webshop.dao.ProductNotFoundException;
import com.aetos.webshop.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return productDao.getById(productId);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId);
        }
    }

    @PostMapping("")
    public Product storeItem(@RequestBody Product product) {
        return productDao.storeItem(product);
    }

    @PutMapping("/{productId}")
    public Product updateItem(@PathVariable Long productId, @RequestBody Product product) {
        try {
            return productDao.updateItem(productId, product);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId);
        }
    }

    @DeleteMapping("/{productId}")
    public Product deleteItem(@PathVariable Long productId) {
        try {
            return productDao.deleteItem(productId);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId);
        }
    }

}
