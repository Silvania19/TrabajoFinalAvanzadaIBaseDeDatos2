package com.utn.TPfinal.utils;

import com.utn.TPfinal.domain.Fee;

public class TestUtils {
   public static Fee aFee(){
       Fee fee= Fee.builder()
               .idFee(1)
               .typeFee("Fee1")
               .priceFee(20)
               .build();
       return fee;
    }
}
