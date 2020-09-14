package com.aetos.webshop.user.dao;

import com.aetos.webshop.product.dao.ProductDao;
import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebshopUser;
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
    public List<WebshopUser> getAll() {
        log.info("Get all users");
        return userRepository.findAll();
    }

    @Override
    public WebshopUser getById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("User not found with id: " + userId);
                    return new UserNotFoundException(userId);
                });
    }

    @Override
    public WebshopUser getByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.info("User not found with email: " + email);
                    return new UserNotFoundException("User not found with email: " + email);
                });
    }

    @Override
    public WebshopUser addUser(WebshopUser user) {
        user.setUserId(null);
        userRepository.save(user);
        log.info("User saved: " + user);
        return user;
    }

    @Override
    public WebshopUser updateUser(Long userId, WebshopUser updatedUser) throws UserNotFoundException {
        WebshopUser userToUpdate = getById(userId);

        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());

        userRepository.save(userToUpdate);
        log.info("Updated user: " + userToUpdate);
        return userToUpdate;
    }

    @Override
    public WebshopUser deleteUser(Long userId) throws UserNotFoundException {
        WebshopUser user = getById(userId);
        userRepository.delete(user);
        log.info("User deleted: " + user);
        return user;
    }

    @Override
    public Map<Product, Integer> getCart(Long userId) throws UserNotFoundException {
        WebshopUser user = getById(userId);
        log.info("Get cart of user: " + user);
        return user.getCart();
    }

    @Override
    public Map<Product, Integer> addToCart(Long userId, Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException {
        WebshopUser user = getById(userId);
        Map<Product, Integer> cart = user.getCart();
        Product product = productDao.getById(productId);

        cart.put(product, cart.getOrDefault(product, 0) + quantity);
        log.info("Product: " + product + " added to cart of user: " + user);
        userRepository.save(user);
        return cart;
    }

    @Override
    public Map<Product, Integer> removeOneFromCart(Long userId, Long productId)
            throws UserNotFoundException, ProductNotFoundException {
        WebshopUser user = getById(userId);
        Map<Product, Integer> cart = user.getCart();
        Product product = productDao.getById(productId);

        if (cart.containsKey(product)) {
            Integer currentQuantity = cart.get(product);
            if (currentQuantity > 1) {
                cart.put(product, currentQuantity - 1);
            } else {
                cart.remove(product);
            }
            userRepository.save(user);
            log.info("Removed one product: " + product + " from cart of user: " + user);
        } else {
            log.info("Remove from cart failed, product not found in cart with productId: " + productId);
        }
        return cart;
    }

    @Override
    public Map<Product, Integer> removeProductFromCart(Long userId, Long productId)
            throws UserNotFoundException, ProductNotFoundException {
        WebshopUser user = getById(userId);
        Map<Product, Integer> cart = user.getCart();
        Product product = productDao.getById(productId);

        cart.remove(product);
        userRepository.save(user);
        log.info("Removed product: " + product + " from cart of user: " + user);
        return cart;
    }

    @Override
    public Map<Product, Integer> updateQuantityOfProductInCart(Long userId, Long productId, Integer updatedQuantity)
            throws UserNotFoundException, ProductNotFoundException {
        WebshopUser user = getById(userId);
        Map<Product, Integer> cart = user.getCart();
        Product product = productDao.getById(productId);

        if (updatedQuantity >= 1) {
            cart.put(product, updatedQuantity);
        } else {
            cart.remove(product);
        }
        userRepository.save(user);
        log.info("Updated quantity: " + updatedQuantity + " of product: " + product + " in cart of user: " + user);
        return cart;
    }

    @Override
    public void clearCart(Long userId) throws UserNotFoundException {
        WebshopUser user = getById(userId);
        getById(userId).getCart().clear();
        userRepository.save(user);
    }

}
