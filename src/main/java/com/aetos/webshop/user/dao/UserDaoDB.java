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
                    return new UserNotFoundException(userId);
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
        log.info("Get cart for user: " + user);
        return user.getCart();
    }

    @Override
    public Map<Product, Integer> addToCart(Long userId, Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException {
        User user = getById(userId);
        Map<Product, Integer> cart = user.getCart();
        Product product = productDao.getById(productId);

        cart.put(product, cart.getOrDefault(product, 0) + quantity);
        log.info("Product: " + product + " added to cart of user: " + user);
        return cart;
    }

    @Override
    public Map<Product, Integer> removeOneFromCart(Long userId, Long productId)
            throws UserNotFoundException, ProductNotFoundException {
        User user = getById(userId);
        Map<Product, Integer> cart = user.getCart();
        Product product = productDao.getById(productId);

        if (cart.containsKey(product)) {
            Integer currentQuantity = cart.get(product);
            if (currentQuantity > 1) {
                cart.put(product, currentQuantity - 1);
            } else {
                cart.remove(product);
            }
            log.info("Removed one product: " + product + " from cart of user: " + user);
        } else {
            log.info("Remove from cart failed, product not found in cart with productId: " + productId);
        }
        return cart;
    }

    @Override
    public Map<Product, Integer> removeProductFromCart(Long userId, Long productId)
            throws UserNotFoundException, ProductNotFoundException {
        User user = getById(userId);
        Map<Product, Integer> cart = user.getCart();
        Product product = productDao.getById(productId);

        cart.remove(product);
        log.info("Removed product: " + product + " from cart of user: " + user);
        return cart;
    }

    @Override
    public Map<Product, Integer> updateQuantityOfProductInCart(Long userId, Long productId, Integer updatedQuantity)
            throws UserNotFoundException, ProductNotFoundException {
        User user = getById(userId);
        Map<Product, Integer> cart = user.getCart();
        Product product = productDao.getById(productId);

        cart.put(product, updatedQuantity);
        log.info("Updated quantity: " + updatedQuantity + " of product: " + product + " in cart of user: " + user);
        return cart;
    }

    @Override
    public void clearCart(Long userId) throws UserNotFoundException {
        getById(userId).getCart().clear();
    }

}
