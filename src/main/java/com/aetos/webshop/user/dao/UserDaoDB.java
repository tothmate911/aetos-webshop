package com.aetos.webshop.user.dao;

import com.aetos.webshop.product.dao.ProductDao;
import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.User;
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
    public List<User> getAll() {
        log.info("Get all users");
        return userRepository.findAll();
    }

    @Override
    public User getById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("User not found with id: " + userId);
                    return new  UserNotFoundException(userId);
                });
    }

    @Override
    public User addUser(User user) {
        user.setUserId(null);
        userRepository.save(user);
        log.info("User saved: " + user);
        return user;
    }

    @Override
    public User updateUser(Long userId, User updatedUser) throws UserNotFoundException {
        User userToUpdate = getById(userId);

        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());

        log.info("Updated user: " + userToUpdate);
        return userToUpdate;
    }

    @Override
    public User deleteUser(Long userId) throws UserNotFoundException {
        User user = getById(userId);
        userRepository.delete(user);
        log.info("User deleted: " + user);
        return user;
    }

    @Override
    public Map<Product, Integer> getCart(Long userId) throws UserNotFoundException {
        User user = getById(userId);
        return user.getCart();
    }

    @Override
    public Map<Product, Integer> addToCart(Long userId, Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException {
        User user = getById(userId);
        Product product = productDao.getById(productId);

        Map<Product, Integer> cart = user.getCart();
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
        return cart;
    }

    @Override
    public Map<Product, Integer> removeFromCart(Long userId, Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException {
        User user = getById(userId);
        Product product = productDao.getById(productId);

        Map<Product, Integer> cart = user.getCart();
        Integer newQuantity = Math.max(cart.getOrDefault(product, 0) - quantity, 0);
        cart.put(product, newQuantity);
        return cart;
    }

    @Override
    public void clearCart(Long userId) throws UserNotFoundException {
        getById(userId).getCart().clear();
    }

}
