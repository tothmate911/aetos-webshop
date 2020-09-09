package com.aetos.webshop.dao;

import com.aetos.webshop.exception.ProductNotFoundException;
import com.aetos.webshop.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    Product getById(Long productId) throws ProductNotFoundException;

    Product storeProduct(Product product);

    Product updateItem(Long productId, Product updatedProduct) throws ProductNotFoundException;

    Product deleteItem(Long productId) throws ProductNotFoundException;

}
