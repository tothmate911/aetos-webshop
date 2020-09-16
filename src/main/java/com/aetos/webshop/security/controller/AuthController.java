package com.aetos.webshop.security.controller;

import com.aetos.webshop.security.model.UserCredentials;
import com.aetos.webshop.security.service.JwtTokenServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenServices jwtTokenServices;

    @PostMapping("/signin")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody UserCredentials data) {
        try {
            String email = data.getEmail();
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));

            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenServices.createToken(email, roles);

            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("roles", roles);
            model.put("token", token);

            return ResponseEntity.ok(model);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }

}