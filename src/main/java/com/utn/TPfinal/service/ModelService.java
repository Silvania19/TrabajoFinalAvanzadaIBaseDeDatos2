package com.utn.TPfinal.service;
import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.domain.Model;
import com.utn.TPfinal.exception.NotFoundException;
import com.utn.TPfinal.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service

public class ModelService {
    @Autowired
    ModelRepository modelRepository;

    public Model getByID(Integer id) throws NotFoundException {
        Optional<Model> model= modelRepository.findById(id);
        return model.get();
    }


}
