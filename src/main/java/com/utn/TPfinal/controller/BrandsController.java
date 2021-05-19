package com.utn.TPfinal.controller;

import com.utn.TPfinal.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandsController {
    @Autowired
    BrandService brandService;

}
