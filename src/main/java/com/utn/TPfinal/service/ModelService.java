package com.utn.TPfinal.service;
import com.utn.TPfinal.domain.Model;
import com.utn.TPfinal.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service

public class ModelService {
    @Autowired
    ModelRepository modelRepository;

    public Model getByID(Integer id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }


}
