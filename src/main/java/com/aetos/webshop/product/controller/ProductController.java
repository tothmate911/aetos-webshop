package com.aetos.webshop.product.controller;

import com.aetos.webshop.product.dao.ProductDao;
import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("")
    public Product storeProduct(@RequestBody Product product) {
        return productDao.storeProduct(product);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        try {
            return productDao.updateItem(productId, updatedProduct);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public Product deleteProduct(@PathVariable Long productId) {
        try {
            return productDao.deleteItem(productId);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
