package com.aetos.webshop.user.dao;

import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebshopUser;

public interface UserDao {

    WebshopUser getMyUserInfo() throws UserNotFoundException;

}
