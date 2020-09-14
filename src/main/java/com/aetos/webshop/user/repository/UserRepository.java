package com.aetos.webshop.user.repository;

import com.aetos.webshop.user.model.WebshopUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<WebshopUser, Long> {

    Optional<WebshopUser> findByEmail(String email);

}
