//package com.aetos.webshop.security.service;
//
//import com.aetos.webshop.user.model.WebshopUser;
//import com.aetos.webshop.user.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.stream.Collectors;
//
//@Component
//@AllArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        WebshopUser webshopUser = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//        return new User(webshopUser.getEmail(), webshopUser.getHashedPassword(),
//                webshopUser.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//    }
//
//}
