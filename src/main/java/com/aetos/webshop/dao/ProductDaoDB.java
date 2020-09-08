package com.aetos.webshop.dao;

import com.aetos.webshop.model.Product;
import com.aetos.webshop.repository.ProductRepository;
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
        log.info("Get all items");
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long productId) {
        log.info("Get item by id: " + productId);
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product storeItem(Product product) {
        productRepository.save(product);
        log.info("Item saved: " + product);
        return product;
    }

    @Override
    public Product updateItem(Long productId, Product updatedProduct) {
        Product productToUpdate = productRepository.findById(productId).orElse(null);
        if (productToUpdate != null) {
            productToUpdate.setName(updatedProduct.getName());
            productToUpdate.setDescription(updatedProduct.getDescription());
            productToUpdate.setCategory(updatedProduct.getCategory());
            productToUpdate.setPrice(updatedProduct.getPrice());
            productToUpdate.setQuantityOnStock(updatedProduct.getQuantityOnStock());

            productRepository.save(productToUpdate);
            log.info("Updated item: " + productToUpdate);
        } else {
            log.info("Failed to update, item with id " + productId + " not found");
        }
        return productToUpdate;
    }

    @Override
    public Product deleteItem(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            log.info("Item deleted: " + product);
        } else {
            log.info("Failed to delete, item with id " + productId + " not found");
        }
        return product;
    }
}
