package com.aetos.webshop.user.dao;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebshopUser;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<WebshopUser> getAll();

    WebshopUser getById(Long userId) throws UserNotFoundException;

    WebshopUser getByEmail(String email) throws UserNotFoundException;

    WebshopUser addUser(WebshopUser user);

    WebshopUser updateUser(Long userId, WebshopUser updatedUser) throws UserNotFoundException;

    WebshopUser deleteUser(Long userId) throws UserNotFoundException;

    Map<Product, Integer> getCart(Long userId) throws UserNotFoundException;

    Map<Product, Integer> addToCart(Long userId, Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException;

    Map<Product, Integer> removeOneFromCart(Long userId, Long productId)
            throws UserNotFoundException, ProductNotFoundException;

    Map<Product, Integer> removeProductFromCart(Long userId, Long productId)
            throws UserNotFoundException, ProductNotFoundException;

    Map<Product, Integer> updateQuantityOfProductInCart(Long userId, Long productId, Integer updatedQuantity)
            throws UserNotFoundException, ProductNotFoundException;

    void clearCart(Long userId) throws UserNotFoundException;

}
