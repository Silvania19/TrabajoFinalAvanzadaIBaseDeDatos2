package com.utn.TPfinal.utils;

import com.utn.TPfinal.domain.*;
import com.utn.TPfinal.domain.dto.MeasuringDto;
import com.utn.TPfinal.domain.dto.RequestLoginDto;
import com.utn.TPfinal.domain.dto.ResponseLoginDto;
import com.utn.TPfinal.domain.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

public class TestUtils {

    public static Pageable aPageable(){

      Pageable pageable = PageRequest.of(1, 10);
     return pageable;
    }

   public static Fee aFee(){
       Fee fee= Fee.builder()
               .idFee(1)
               .typeFee("Fee1")
               .priceFee(20)
               .build();
       return fee;
    }

    public static Client aClient(){
        Client client = new Client();
        client.setId(1);
        client.setName("carlos");
        return client;
    }

    public static List<UserDto> aListUserDto(){
        return List.of(aUserDto());
    }
    public static List<Client> aListUser(){
        return List.of(aClient());
    }
    public static Bill aBill() {
       Bill bill= Bill.builder()
               .idBill(1)
               .amount(100.00)
               .pay(false)
               .client(aClient())
               .address(aAddress())
               .build();
       return bill;
    }
    public static Meter aMeter(){
        Meter meter= Meter.builder()
                .serialNumber("12345")
                .passwordMeter("123")
                .fee(aFee())
                .address(aAddress())
                //.model(aModel())
                .build();
        return  meter;
    }

    public static Address aAddress(){
        Address address= Address.builder()
                .idAddress(1)
                //.nameAddress("nameAddress")
                .client(aClient())
                .build();
        return  address;
    }

    public static Model aModel(){
    Model model = Model.builder()
            .idModel(1)
            .description("model1")
            .brands(aBrand())
            .build();
    return  model;
    }
    public  static  Brands aBrand(){
        Brands brand= Brands.builder()
                .idBrand(1)
                .description("brand1")
                .build();
        return brand;
    }
    public static Page<Bill> aPageBills(){
        return new PageImpl<>(List.of(aBill()));
    }

    public static RequestLoginDto aRequestLogin(){
        RequestLoginDto requestLoginDto= RequestLoginDto.builder()
                .name("silvania")
                .password("12334")
                .build();
        return  requestLoginDto;
    }
    public static ResponseLoginDto aResponseLogin(){
        ResponseLoginDto responseLoginDto= ResponseLoginDto.builder()
                .token("1223456uhgsdfghjkmnbvcsg")
                .build();
        return  responseLoginDto;
    }
    public  static UserDto aUserDto(){
        UserDto userDto= UserDto.builder()
                        .id(1)
                        .name("carlos")
                        .build();
        return  userDto;
    }
    public static Employee aEnployee(){
        Employee employee= new Employee();
        employee.setId(1);
        employee.setName("silvania");
        employee.setLastname("ortega");
        employee.setPassword("1234");
        return  employee;
    }
    public static Client aClient2(){
        Client client = new Client();
        client.setId(1);
        client.setName("lucas");
        client.setLastname("ortega");
        client.setPassword("1234");
        return client;
    }
    public  static  Measuring newMeasuring(){
        Measuring measuring=Measuring.builder()
                .idMeasuring(1)
                //.meter(aMeter())
                .value(100)
                .priceMeasuring(200)
                .build();
        return measuring;
    }

    public static Page<Measuring> aPageMeasuring() {
        return new PageImpl<>(List.of(newMeasuring()));
    }

    public static List<Measuring> aListMeasuring(){
        List<Measuring> measurings= List.of(newMeasuring());
        return measurings;
    }

    public static MeasuringDto aMeasuringDto(){
        MeasuringDto measuringDto= MeasuringDto.builder()
                .serialNumber("11111")
                .password("1234")
                .value(10).build();
        return  measuringDto;
    }

    }

