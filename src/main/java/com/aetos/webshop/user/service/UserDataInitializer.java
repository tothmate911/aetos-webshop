package com.aetos.webshop.user.service;

import com.aetos.webshop.user.dao.AdminUserDao;
import com.aetos.webshop.user.model.WebshopUser;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
@Profile("production")
public class UserDataInitializer {

    private AdminUserDao adminUserDao;

    private PasswordEncoder passwordEncoder;

    public UserDataInitializer(AdminUserDao adminUserDao) {
        this.adminUserDao = adminUserDao;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @PostConstruct
    public void init() {
        adminUserDao.addUser(
                WebshopUser.builder()
                        .email("user1@gmail.com")
                        .hashedPassword(passwordEncoder.encode("user1"))
                        .firstName("István")
                        .lastName("Nagy")
                        .roles(new ArrayList<>(Collections.singletonList("ROLE_USER")))
                        .build()
        );

        adminUserDao.addUser(
                WebshopUser.builder()
                        .email("user2@gmail.com")
                        .hashedPassword(passwordEncoder.encode("user2"))
                        .firstName("László")
                        .lastName("Kis")
                        .roles(new ArrayList<>(Collections.singletonList("ROLE_USER")))
                        .build()
        );

        adminUserDao.addUser(
                WebshopUser.builder()
                        .email("admin@gmail.com")
                        .hashedPassword(passwordEncoder.encode("admin"))
                        .firstName("Admin")
                        .lastName("Admin")
                        .roles(new ArrayList<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")))
                        .build()
        );
    }

}
