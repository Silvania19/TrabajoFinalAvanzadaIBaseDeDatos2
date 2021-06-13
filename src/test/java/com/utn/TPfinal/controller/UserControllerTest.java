package com.utn.TPfinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.TPfinal.domain.dto.RequestLoginDto;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.ClientService;
import com.utn.TPfinal.service.EmployeeService;
import com.utn.TPfinal.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.utn.TPfinal.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    ClientService clientService;
    EmployeeService employeeService;
    ModelMapper modelMapper;
    ObjectMapper objectMapper;
    UserController userController;
    @BeforeEach
    public  void  setUp(){
        clientService= mock(ClientService.class);
        employeeService= mock(EmployeeService.class);
        modelMapper= mock(ModelMapper.class);
        objectMapper= mock(ObjectMapper.class);
        userController= new UserController(clientService, objectMapper, modelMapper, employeeService);
    }
    @Test
    public void loginClientOk(){
        //given
        RequestLoginDto requestLoginDto= aRequestLogin();
        when(clientService.findByNameAndPassword(requestLoginDto.getName(), requestLoginDto.getPassword()))
                .thenReturn(aClient2());
        when(modelMapper.map(aClient2(), UserDto.class)).thenReturn(aUserDto());
        //when
        ResponseEntity responseEntity= userController.loginClient(requestLoginDto);
        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    public void loginClientUnauthorized(){
        //given
        when(clientService.findByNameAndPassword("lucas", aRequestLogin().getPassword()))
                .thenReturn(aClient2());
        //then
        ResponseEntity responseEntity= userController.loginClient(aRequestLogin());
        //WHEN
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }
    @Test
    public void loginBackofficeOk(){
        //given
        RequestLoginDto requestLoginDto= aRequestLogin();
        when(employeeService.findByNameAndPassword(requestLoginDto.getName(), requestLoginDto.getPassword()))
                .thenReturn(aEnployee());
        when(modelMapper.map(aEnployee(), UserDto.class)).thenReturn(aUserDto());
        //when
        ResponseEntity responseEntity= userController.loginBackoffice(requestLoginDto);
        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
    @Test
    public void loginBackofficeUnauthorized(){
        //given
        when(employeeService.findByNameAndPassword("lucas", aRequestLogin().getPassword()))
                .thenReturn(aEnployee());
        //then
        ResponseEntity responseEntity= userController.loginBackoffice(aRequestLogin());
        //WHEN
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }
}
