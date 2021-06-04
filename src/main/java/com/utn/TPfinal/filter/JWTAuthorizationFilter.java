package com.utn.TPfinal.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.utn.TPfinal.util.constant.JWT_HEADER;
import static com.utn.TPfinal.util.constant.JWT_PREFIX;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    ObjectMapper objectMapper;

    public JWTAuthorizationFilter() {
        this.objectMapper = new ObjectMapper();
    }

    /*para check que los   si el request es valido*/
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    }

    private boolean containsJWT(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(JWT_HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(JWT_PREFIX))
            return false;
        return true;
    }

}
