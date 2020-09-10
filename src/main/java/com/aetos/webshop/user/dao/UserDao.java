package com.aetos.webshop.user.dao;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebShopUser;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<WebShopUser> getAll();

    WebShopUser getById(Long userId) throws UserNotFoundException;

    WebShopUser addUser(WebShopUser webShopUser);

    WebShopUser updateUser(Long userId, WebShopUser updatedWebShopUser) throws UserNotFoundException;

    WebShopUser deleteUser(Long userId) throws UserNotFoundException;

    Map<Product, Integer> getCart(Long userId) throws UserNotFoundException;

    Map<Product, Integer> addToCart(Long userId, Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException;

    Map<Product, Integer> removeFromCart(Long userId, Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException;

    void clearCart(Long userId) throws UserNotFoundException;

}
