//package com.aetos.webshop.user.service;
//
//import com.aetos.webshop.user.dao.UserDao;
//import com.aetos.webshop.user.model.WebshopUser;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.Arrays;
//import java.util.Collections;
//
//@Service
//@Profile("production")
//public class UserDataInitializer {
//
//    private UserDao userDao;
//
//    private PasswordEncoder passwordEncoder;
//
//    public UserDataInitializer(UserDao userDao) {
//        this.userDao = userDao;
//        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @PostConstruct
//    public void init() {
//        userDao.addUser(
//                WebshopUser.builder()
//                        .email("user1@gmail.com")
//                        .hashedPassword(passwordEncoder.encode("user1"))
//                        .firstName("István")
//                        .lastName("Nagy")
//                        .roles(Collections.singletonList("ROLE_USER"))
//                        .build()
//        );
//
//        userDao.addUser(
//                WebshopUser.builder()
//                        .email("user2@gmail.com")
//                        .hashedPassword(passwordEncoder.encode("user2"))
//                        .firstName("László")
//                        .lastName("Kis")
//                        .roles(Collections.singletonList("ROLE_USER"))
//                        .build()
//        );
//
//        userDao.addUser(
//                WebshopUser.builder()
//                        .email("admin@gmail.com")
//                        .hashedPassword(passwordEncoder.encode("admin"))
//                        .firstName("Admin")
//                        .lastName("Admin")
//                        .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
//                        .build()
//        );
//    }
//
//}
