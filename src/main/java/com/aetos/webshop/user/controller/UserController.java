package com.aetos.webshop.user.controller;

import com.aetos.webshop.user.dao.UserDao;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebshopUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/me")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class UserController {

    private UserDao userDao;

    @GetMapping("")
    public WebshopUser getMe() {
        try {
            return userDao.getMe();
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("")
    public WebshopUser updateMe(@RequestBody WebshopUser updatedUser) {
        userDao.updateMe(updatedUser);
    }

}
