package com.aetos.webshop.user.dao;

import com.aetos.webshop.product.dao.ProductDao;
import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebShopUser;
import com.aetos.webshop.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class UserDaoDB implements UserDao {

    private UserRepository userRepository;

    private ProductDao productDao;

    @Override
    public List<WebShopUser> getAll() {
        log.info("Get all users");
        return userRepository.findAll();
    }

    @Override
    public WebShopUser getById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("User not found with id: " + userId);
                    return new  UserNotFoundException(userId);
                });
    }

    @Override
    public WebShopUser addUser(WebShopUser webShopUser) {
        webShopUser.setUserId(null);
        userRepository.save(webShopUser);
        log.info("User saved: " + webShopUser);
        return webShopUser;
    }

    @Override
    public WebShopUser updateUser(Long userId, WebShopUser updatedWebShopUser) throws UserNotFoundException {
        WebShopUser webShopUserToUpdate = getById(userId);

        webShopUserToUpdate.setFirstName(updatedWebShopUser.getFirstName());
        webShopUserToUpdate.setLastName(updatedWebShopUser.getLastName());

        log.info("Updated user: " + webShopUserToUpdate);
        return webShopUserToUpdate;
    }

    @Override
    public WebShopUser deleteUser(Long userId) throws UserNotFoundException {
        WebShopUser webShopUser = getById(userId);
        userRepository.delete(webShopUser);
        log.info("User deleted: " + webShopUser);
        return webShopUser;
    }

    @Override
    public Map<Product, Integer> getCart(Long userId) throws UserNotFoundException {
        WebShopUser webShopUser = getById(userId);
        return webShopUser.getCart();
    }

    @Override
    public Map<Product, Integer> addToCart(Long userId, Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException {
        WebShopUser webShopUser = getById(userId);
        Product product = productDao.getById(productId);

        Map<Product, Integer> cart = webShopUser.getCart();
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
        return cart;
    }

    @Override
    public Map<Product, Integer> removeFromCart(Long userId, Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException {
        WebShopUser webShopUser = getById(userId);
        Product product = productDao.getById(productId);

        Map<Product, Integer> cart = webShopUser.getCart();
        Integer newQuantity = Math.max(cart.getOrDefault(product, 0) - quantity, 0);
        cart.put(product, newQuantity);
        return cart;
    }

    @Override
    public void clearCart(Long userId) throws UserNotFoundException {
        getById(userId).getCart().clear();
    }

}
