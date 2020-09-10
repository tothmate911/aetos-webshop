package com.aetos.webshop.product.dao;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    Product getById(Long productId) throws ProductNotFoundException;

    Product storeProduct(Product product);

    Product updateItem(Long productId, Product updatedProduct) throws ProductNotFoundException;

    Product deleteItem(Long productId) throws ProductNotFoundException;

}
