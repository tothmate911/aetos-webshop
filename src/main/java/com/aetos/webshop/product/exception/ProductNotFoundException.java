package com.aetos.webshop.product.exception;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(Long productId) {
        super("Product not found with id: " + productId);
    }
}
