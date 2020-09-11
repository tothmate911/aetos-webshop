package com.aetos.webshop.user.controller;

import com.aetos.webshop.user.dao.UserDao;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

}
