//package com.aetos.webshop.security.service;
//
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@AllArgsConstructor
//public class JwtTokenFilter extends GenericFilterBean {
//
//    private JwtTokenServices jwtTokenServices;
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res,
//                         FilterChain filterChain) throws IOException, ServletException {
//        String token = jwtTokenServices.getTokenFromRequest((HttpServletRequest) req);
//        if (token != null && jwtTokenServices.validateToken(token)) {
//            Authentication auth = jwtTokenServices.parseUserFromTokenInfo(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//        filterChain.doFilter(req, res);
//    }
//}
