package com.utn.TPfinal.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.TPfinal.domain.TypeUser;
import com.utn.TPfinal.domain.User;
import com.utn.TPfinal.domain.dto.RequestLoginDto;
import com.utn.TPfinal.domain.dto.ResponseLoginDto;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.utn.TPfinal.util.constant.JWT_SECRET;

@Slf4j/*@Slf4j
Creates private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);*/
@RestController
@RequestMapping("/")
public class UserController {

    private UserService userService;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;
    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @PostMapping("login")
    public ResponseEntity<ResponseLoginDto> login(@RequestBody RequestLoginDto requestLoginDto) {
        log.info(requestLoginDto.toString());
        User user = userService.findByNameAndPassword(requestLoginDto.getName(), requestLoginDto.getPassword());
        if (user!=null){
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok(ResponseLoginDto.builder().token(this.generateToken(userDto, user.typeUser())).build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String generateToken(UserDto userDto, TypeUser typeUser) {
        String authority;

        if(typeUser.getDescription().equals("client"))
        {
            authority="CLIENT";
        }
        else
        {
            authority="BACKOFFICE";
        }
        try {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
            String token = Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(userDto.getName())
                    .claim("user", objectMapper.writeValueAsString(userDto))
                    .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
            return  token;
        } catch(Exception e) {
            return "dummy";
        }



    }


    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @PostMapping
    public void addUser(@RequestBody User user) {
         userService.add(user);
    }

}
