package com.aetos.webshop.user.repository;

import com.aetos.webshop.user.model.WebShopUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<WebShopUser, Long> {
}
