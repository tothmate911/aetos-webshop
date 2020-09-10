package com.aetos.webshop.user.service;

import com.aetos.webshop.user.dao.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("production")
@AllArgsConstructor
public class UserDataInitializer {

    private UserDao userDao;

//    @PostConstruct
//    public void init() {
//
//    }

}
