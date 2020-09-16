package com.aetos.webshop.user.controller;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.dao.MyUserDao;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.PublicUserInfo;
import com.aetos.webshop.user.model.WebshopUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/me")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class MyUserController {

    private MyUserDao myUserDao;

    @GetMapping("")
    public PublicUserInfo getMe() {
        try {
            return myUserDao.getMe();
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("")
    public PublicUserInfo updateMe(@RequestBody WebshopUser updatedUser) {
        try {
            return myUserDao.updateMe(updatedUser);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/MyCart")
    public Map<Product, Integer> getMyCart() {
        try {
            return myUserDao.getMyCart();
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PutMapping("/addToMyCart")
    public Map<Product, Integer> addToMyCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            return myUserDao.addToMyCart(productId, quantity);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/removeOneFromMyCart")
    public Map<Product, Integer> removeOneFromMyCart(@RequestParam Long productId) {
        try {
            return myUserDao.removeOneFromMyCart(productId);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/removeProductFromMyCart")
    public Map<Product, Integer> removeProductFromMyCart(@RequestParam Long productId) {
        try {
            return myUserDao.removeProductFromMyCart(productId);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/updateQuantityOfProductInMyCart")
    public Map<Product, Integer> updateQuantityOfProductInMyCart(@RequestParam Long productId,
                                                                 @RequestParam Integer updatedQuantity) {
        try {
            return myUserDao.updateQuantityOfProductInMyCart(productId, updatedQuantity);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/clearMyCart")
    public void clearMyCart() {
        try {
            myUserDao.clearMyCart();
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
