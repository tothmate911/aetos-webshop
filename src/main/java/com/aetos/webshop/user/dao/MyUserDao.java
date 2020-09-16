package com.aetos.webshop.user.dao;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebshopUser;

import java.util.Map;

public interface MyUserDao {

    WebshopUser getMe() throws UserNotFoundException;

    WebshopUser updateMe(WebshopUser updatedUser) throws UserNotFoundException;

    Map<Product, Integer> getMyCart() throws UserNotFoundException;

    Map<Product, Integer> addToMyCart(Long productId, Integer quantity) throws UserNotFoundException, ProductNotFoundException;

    Map<Product, Integer> removeOneFromMyCart(Long productId)
            throws UserNotFoundException, ProductNotFoundException;

    Map<Product, Integer> removeProductFromMyCart(Long productId)
            throws UserNotFoundException, ProductNotFoundException;

    Map<Product, Integer> updateQuantityOfProductInMyCart(Long productId, Integer updatedQuantity)
            throws UserNotFoundException, ProductNotFoundException;

    void clearMyCart() throws UserNotFoundException;

}
