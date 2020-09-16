package com.aetos.webshop.user.controller;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.dao.AdminUserDao;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebshopUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/users")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class AdminUserController {

    private AdminUserDao adminUserDao;

    @GetMapping("")
    public List<WebshopUser> getAll() {
        return adminUserDao.getAll();
    }

    @GetMapping("/{userId}")
    public WebshopUser getById(@PathVariable Long userId) {
        try {
            return adminUserDao.getById(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("")
    public WebshopUser addUser(@RequestBody WebshopUser user) {
        return adminUserDao.addUser(user);
    }

    @PutMapping("/{userId}")
    public WebshopUser updateUser(@PathVariable Long userId, @RequestBody WebshopUser updatedUser) {
        try {
            return adminUserDao.updateUser(userId, updatedUser);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public WebshopUser deleteUser(@PathVariable Long userId) {
        try {
            return adminUserDao.deleteUser(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{userId}/cart")
    public Map<Product, Integer> getCart(@PathVariable Long userId) {
        try {
            return adminUserDao.getCart(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/addToCart")
    public Map<Product, Integer> addToCart(@PathVariable Long userId,
                                           @RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            return adminUserDao.addToCart(userId, productId, quantity);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/removeOneFromCart")
    public Map<Product, Integer> removeOneFromCart(@PathVariable Long userId,
                                                   @RequestParam Long productId) {
        try {
            return adminUserDao.removeOneFromCart(userId, productId);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/removeProductFromCart")
    public Map<Product, Integer> removeProductFromCart(@PathVariable Long userId,
                                                       @RequestParam Long productId) {
        try {
            return adminUserDao.removeProductFromCart(userId, productId);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/updateQuantityOfProductInCart")
    public Map<Product, Integer> updateQuantityOfProductInCart(@PathVariable Long userId,
                                                               @RequestParam Long productId,
                                                               @RequestParam Integer updatedQuantity) {
        try {
            return adminUserDao.updateQuantityOfProductInCart(userId, productId, updatedQuantity);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/clearCart")
    public void clearCart(@PathVariable Long userId) {
        try {
            adminUserDao.clearCart(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
