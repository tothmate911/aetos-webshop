package com.aetos.webshop.user.controller;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.dao.UserDao;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class UserController {

    private UserDao userDao;

    @GetMapping("")
    public List<User> getAll() {
        return userDao.getAll();
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable Long userId) {
        try {
            return userDao.getById(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("")
    public User addUser(@RequestBody User user) {
        return userDao.addUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        try {
            return userDao.updateUser(userId, updatedUser);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public User deleteUser(@PathVariable Long userId) {
        try {
            return userDao.deleteUser(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{userId}/cart")
    public Map<Product, Integer> getCart(@PathVariable Long userId) {
        try {
            return userDao.getCart(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/addToCart")
    public Map<Product, Integer> addToCart(@PathVariable Long userId,
                                           @RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            return userDao.addToCart(userId, productId, quantity);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/removeOneFromCart")
    public Map<Product, Integer> removeOneFromCart(@PathVariable Long userId,
                                                   @RequestParam Long productId) {
        try {
            return userDao.removeOneFromCart(userId, productId);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/removeProductFromCart")
    public Map<Product, Integer> removeProductFromCart(@PathVariable Long userId,
                                                       @RequestParam Long productId) {
        try {
            return userDao.removeProductFromCart(userId, productId);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/updateQuantityOfProductInCart")
    public Map<Product, Integer> updateQuantityOfProductInCart(@PathVariable Long userId,
                                                               @RequestParam Long productId,
                                                               @RequestParam Integer updatedQuantity) {
        try {
            return userDao.updateQuantityOfProductInCart(userId, productId, updatedQuantity);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/clearCart")
    public void clearCart(@PathVariable Long userId) {
        try {
            userDao.clearCart(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
