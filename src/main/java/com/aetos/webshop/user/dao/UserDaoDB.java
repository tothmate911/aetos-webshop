package com.aetos.webshop.user.dao;

import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebshopUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class UserDaoDB implements UserDao {

    private AdminUserDao adminUserDao;

    private Long getSignedInUserId() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        WebshopUser user = adminUserDao.getByEmail(email);
        return user.getUserId();
    }

    @Override
    public WebshopUser getMe() throws UserNotFoundException {
        WebshopUser user = adminUserDao.getById(getSignedInUserId());

        //I don't want to send these back for a simple user info request
        user.setHashedPassword(null);
        user.setRoles(null);

        return user;
    }

    @Override
    public void updateMe(WebshopUser updatedUser) throws UserNotFoundException {
        adminUserDao.updateUser(getSignedInUserId(), updatedUser);
    }

    @Override
    public Map<Product, Integer> getMyCart() throws UserNotFoundException {
        return adminUserDao.getCart(getSignedInUserId());
    }

    @Override
    public Map<Product, Integer> addToMyCart(Long productId, Integer quantity)
            throws UserNotFoundException, ProductNotFoundException {
        return adminUserDao.addToCart(getSignedInUserId(), productId, quantity);
    }

    @Override
    public Map<Product, Integer> removeOneFromMyCart(Long productId)
            throws UserNotFoundException, ProductNotFoundException {
        return adminUserDao.removeOneFromCart(getSignedInUserId(), productId);
    }

    @Override
    public Map<Product, Integer> removeProductFromMyCart(Long productId)
            throws UserNotFoundException, ProductNotFoundException {
        return adminUserDao.removeProductFromCart(getSignedInUserId(), productId);
    }

    @Override
    public Map<Product, Integer> updateQuantityOfProductInMyCart(Long productId, Integer updatedQuantity)
            throws UserNotFoundException, ProductNotFoundException {
        return adminUserDao.updateQuantityOfProductInCart(getSignedInUserId(), productId, updatedQuantity);
    }

    @Override
    public void clearMyCart() throws UserNotFoundException {
        adminUserDao.clearCart(getSignedInUserId());
    }

}
