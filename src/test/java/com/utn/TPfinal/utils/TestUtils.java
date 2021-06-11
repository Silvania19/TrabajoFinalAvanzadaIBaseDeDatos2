package com.utn.TPfinal.utils;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.domain.User;
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

    public static Bill aBill() throws ParseException{
       Bill bill= Bill.builder()
               .idBill(1)
               .amount(100.00)
               .pay(false)
               .firstMeasurement(aDate1())
               .lastMeasurement(aDate2())
               .client(aClient())
               .build();
       return bill;
    }

    public static Page<Bill> aPageBills() throws ParseException {
        return new PageImpl<>(List.of(aBill()));
    }
}
