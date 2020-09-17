package com.aetos.webshop.security.service;

import com.aetos.webshop.security.exception.UserAlreadyExistsException;
import com.aetos.webshop.user.dao.AdminUserDao;
import com.aetos.webshop.user.model.WebshopUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
@Slf4j
public class RegistrationService {

    private AdminUserDao adminUserDao;

    private PasswordEncoder passwordEncoder;

    public RegistrationService(AdminUserDao adminUserDao) {
        this.adminUserDao = adminUserDao;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public WebshopUser register(WebshopUser userData) throws UserAlreadyExistsException {
        String email = userData.getEmail();
        if (!adminUserDao.exists(email)) {
            WebshopUser userToRegister = WebshopUser.builder()
                    .email(email)
                    //the password of userData is not hashed yet, so it needs to be
                    .password(passwordEncoder.encode(userData.getPassword()))
                    .firstName(userData.getFirstName())
                    .lastName(userData.getLastName())
                    .roles(new ArrayList<>(Collections.singletonList("ROLE_USER")))
                    .build();

            log.info("Register user: " + userToRegister);
            return adminUserDao.addUser(userToRegister);
        } else {
            String message = "Registration failed, there is already a user registered with this email: " + email;
            log.info(message);
            throw new UserAlreadyExistsException(message);
        }
    }

}
