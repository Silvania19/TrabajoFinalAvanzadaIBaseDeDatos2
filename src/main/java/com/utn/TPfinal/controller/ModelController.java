package com.utn.TPfinal.controller;

import com.utn.TPfinal.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelController {
    @Autowired
    ModelService modelService;
}
