package com.aetos.webshop.dao;

import com.aetos.webshop.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    Product getById(Long productId);

    Product storeItem(Product product);

    Product updateItem(Long productId, Product updatedProduct);

    Product deleteItem(Long productId);

}
