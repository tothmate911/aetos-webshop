package com.aetos.webshop.product.dao;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductDaoDB implements ProductDao {

    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        log.info("Get all products");
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long productId) throws ProductNotFoundException {
        log.info("Get product by id: " + productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.info("Product not found with id: " + productId);
                    return new ProductNotFoundException(productId);
                });
    }

    @Override
    public Product storeProduct(Product product) {
        product.setProductId(null);
        productRepository.save(product);
        log.info("Product saved: " + product);
        return product;
    }

    @Override
    public Product updateItem(Long productId, Product updatedProduct) throws ProductNotFoundException {
        Product productToUpdate = getById(productId);

        productToUpdate.setName(updatedProduct.getName());
        productToUpdate.setDescription(updatedProduct.getDescription());
        productToUpdate.setImageUrl(updatedProduct.getImageUrl());
        productToUpdate.setCategory(updatedProduct.getCategory());
        productToUpdate.setPrice(updatedProduct.getPrice());
        productToUpdate.setQuantityOnStock(updatedProduct.getQuantityOnStock());

        productRepository.save(productToUpdate);
        log.info("Updated product: " + productToUpdate);
        return productToUpdate;
    }

    @Override
    public Product deleteItem(Long productId) throws ProductNotFoundException {
        Product product = getById(productId);
        productRepository.delete(product);
        log.info("Product deleted: " + product);
        return product;
    }

}
