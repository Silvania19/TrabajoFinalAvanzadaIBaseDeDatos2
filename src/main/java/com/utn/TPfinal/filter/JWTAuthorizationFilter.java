package com.utn.TPfinal.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.TPfinal.domain.dto.UserDto;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.utn.TPfinal.util.constant.*;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    ObjectMapper objectMapper;

    public JWTAuthorizationFilter() {

        this.objectMapper = new ObjectMapper();
    }

    /*para check que los   si el request es valido*/
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {
            if (containsJWT(httpServletRequest)) {
                Claims claims = validateToken(httpServletRequest);
                if (claims.get("user") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) httpServletResponse).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    /** return un Claims,  desencripta y valida si existe el token**/
    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(JWT_HEADER).replace(JWT_PREFIX, "");

        return Jwts.parser().setSigningKey(JWT_SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();//This line will throw an exception if it is not a signed JWS (as expected)
    }

    /**añade la configuración necesaria al contexto de Spring para autorizar la petición**/
    private void setUpSpringAuthentication(Claims claims) {
        try {
            List<String> authorities = (List) claims.get("authorities");
            String userClaim = (String) claims.get("user");
            UserDto user = objectMapper.readValue(userClaim, UserDto.class);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
                    authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (JsonProcessingException e) {
            SecurityContextHolder.clearContext();
        }


    }
    /**
     * Comprueba la existencia del token.*/
    private boolean containsJWT(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(JWT_HEADER);// return el encabezado de la solicitud
        if (authenticationHeader == null || !authenticationHeader.startsWith(JWT_PREFIX))
            return false;
        return true;
    }

}
