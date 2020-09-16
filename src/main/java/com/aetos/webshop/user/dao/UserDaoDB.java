package com.aetos.webshop.user.dao;

import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebshopUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserDaoDB implements UserDao {

    private AdminUserDao adminUserDao;

    @Override
    public WebshopUser getMyUserInfo() throws UserNotFoundException {
        return adminUserDao.getById(getSignedInUserId());
    }

    private Long getSignedInUserId() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        WebshopUser user = adminUserDao.getByEmail(email);
        return user.getUserId();
    }
}
