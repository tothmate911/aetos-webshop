package com.aetos.webshop.user.dao;

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

    @Override
    public List<User> getAll() {
        log.info("Get all users");
        return userRepository.findAll();
    }

    @Override
    public User getById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        return null;
    }

    @Override
    public User deleteUser(Long userId) throws UserNotFoundException {
        return null;
    }

    @Override
    public Map<Product, Integer> getCart(Long userId) throws UserNotFoundException {
        return null;
    }

    @Override
    public Map<Product, Integer> addToCart(Long userId, Long productId, Integer quantity) throws UserNotFoundException, ProductNotFoundException {
        return null;
    }

    @Override
    public Map<Product, Integer> removeFromCart(Long userId, Long productId, Integer quantity) throws UserNotFoundException, ProductNotFoundException {
        return null;
    }

    @Override
    public void clearCart(Long userId) throws UserNotFoundException {

    }
}
