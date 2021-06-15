package com.utn.TPfinal.utils;

import com.utn.TPfinal.domain.*;
import com.utn.TPfinal.domain.dto.RequestLoginDto;
import com.utn.TPfinal.domain.dto.ResponseLoginDto;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.projecciones.Consumption;
import com.utn.TPfinal.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class TestUtils {

    public static Date aDate1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.parse("03-26-2020");
    }

    public static Date aDate2() throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.parse("03-27-2020");
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

    public static Bill aBill() {
       Bill bill= Bill.builder()
               .idBill(1)
               .amount(100.00)
               .pay(false)
               .client(aClient())
               .build();
       return bill;
    }
    public static Meter aMeter(){
        Meter meter= Meter.builder()
                .serialNumber("12345")
                .passwordMeter("123")
                .fee(aFee())
                .address(aAdrress())
                //.model(aModel())
                .build();
        return  meter;
    }
    public  static Address aAdrress(){
        Address address= Address.builder()
                .idAddress(1)
                .nameAddress("address1")
                .numberAddress("1234")
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
                        .lastname("ortega")
                        .name("silvania")
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

   /* public static Consumption newConsumption(){
        Consumption consumption= new () {

        }
    }*/
}
