package com.utn.TPfinal.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.Employee;
import com.utn.TPfinal.domain.TypeUser;
import com.utn.TPfinal.domain.User;
import com.utn.TPfinal.domain.dto.RequestLoginDto;
import com.utn.TPfinal.domain.dto.ResponseLoginDto;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.ClientService;

import com.utn.TPfinal.service.EmployeeService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.utn.TPfinal.util.constant.JWT_SECRET;

@Slf4j/*@Slf4j
Creates private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);*/
@RestController
@RequestMapping("/")
public class UserController {

    private ClientService clientService;
    private EmployeeService employeeService;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;
    @Autowired
    public UserController(ClientService clientService, ObjectMapper objectMapper, ModelMapper modelMapper, EmployeeService employeeService) {
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }


    @PostMapping("client/login")
    public ResponseEntity<ResponseLoginDto> loginClient(@RequestBody RequestLoginDto requestLoginDto) {
        log.info(requestLoginDto.toString());
        Client client = clientService.findByNameAndPassword(requestLoginDto.getName(), requestLoginDto.getPassword());
        if (client!=null){
            UserDto userDto = modelMapper.map(client, UserDto.class);
            return ResponseEntity.ok(ResponseLoginDto.builder().token(this.generateToken(userDto, client.typeUser())).build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("backoffice/login")
    public ResponseEntity<ResponseLoginDto> loginBackoffice(@RequestBody RequestLoginDto requestLoginDto) {
        log.info(requestLoginDto.toString());
        Employee employee = employeeService.findByNameAndPassword(requestLoginDto.getName(), requestLoginDto.getPassword());
        if (employee!=null){
            UserDto userDto = modelMapper.map(employee, UserDto.class);
            return ResponseEntity.ok(ResponseLoginDto.builder().token(this.generateToken(userDto, employee.typeUser())).build());
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
                    .setExpiration(new Date(System.currentTimeMillis() +  36000000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
            return  token;
        } catch(Exception e) {
            return "dummy";
        }
    }

    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @PostMapping
    public void addUser(@RequestBody Client user) {
        clientService.add(user);
    }

}
