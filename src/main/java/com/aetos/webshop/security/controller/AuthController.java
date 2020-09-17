package com.aetos.webshop.security.controller;

import com.aetos.webshop.security.exception.UserAlreadyExistsException;
import com.aetos.webshop.security.model.UserCredentials;
import com.aetos.webshop.security.service.JwtTokenServices;
import com.aetos.webshop.security.service.RegistrationService;
import com.aetos.webshop.user.model.WebshopUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtTokenServices jwtTokenServices;

    private RegistrationService registrationService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody UserCredentials data) {
        try {
            String email = data.getEmail();
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));

            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenServices.createToken(email, roles);

            Map<String, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("roles", roles);
            model.put("token", token);

            log.info("User signed in: " + model.toString());
            return ResponseEntity.ok(model);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody WebshopUser userData) {
        Map<String, Object> model = new HashMap<>();
        try {
            WebshopUser registeredUser = registrationService.register(userData);

            model.put("successful registration", true);
            model.put("email", registeredUser.getEmail());
            model.put("firstName", registeredUser.getFirstName());
            model.put("lastName", registeredUser.getLastName());

            return ResponseEntity.ok(model);

        } catch (UserAlreadyExistsException e) {
            model.put("successful registration", false);
            model.put("message", e.getMessage());
            return ResponseEntity.ok(model);
        }
    }

}
