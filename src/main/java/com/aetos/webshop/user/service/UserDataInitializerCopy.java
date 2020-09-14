package com.aetos.webshop.user.service;

import com.aetos.webshop.user.dao.UserDao;
import com.aetos.webshop.user.model.WebshopUser;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@Service
@Profile("production")
public class UserDataInitializerCopy {

    private UserDao userDao;


    public UserDataInitializerCopy(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostConstruct
    public void init() {
        userDao.addUser(
                WebshopUser.builder()
                        .email("user1@gmail.com")
                        .hashedPassword("user1")
                        .firstName("István")
                        .lastName("Nagy")
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()
        );

        userDao.addUser(
                WebshopUser.builder()
                        .email("user2@gmail.com")
                        .hashedPassword("user2")
                        .firstName("László")
                        .lastName("Kis")
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()
        );

        userDao.addUser(
                WebshopUser.builder()
                        .email("admin@gmail.com")
                        .hashedPassword("admin")
                        .firstName("Admin")
                        .lastName("Admin")
                        .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                        .build()
        );
    }

}
