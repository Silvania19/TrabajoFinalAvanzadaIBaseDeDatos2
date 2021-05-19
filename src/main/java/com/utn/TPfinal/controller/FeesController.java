package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeesController {
    @Autowired
    FeeService feeService;
    @PostMapping
    public void addPersona(@RequestBody Fee fee) {
         feeService.add(fee);
    }

}
