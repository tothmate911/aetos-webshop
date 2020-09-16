package com.aetos.webshop.user.controller;

import com.aetos.webshop.user.dao.UserDao;
import com.aetos.webshop.user.model.WebshopUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class UserController {

    private UserDao userDao;

    @GetMapping("/userinfo")
    public WebshopUser getMyUserInfo() {
        return userDao.getMyUserInfo();
    }

}
