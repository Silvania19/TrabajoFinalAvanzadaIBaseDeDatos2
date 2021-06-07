package com.utn.TPfinal.controller;
import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.domain.User;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.service.FeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@Slf4j
@RestController
@RequestMapping("/fee")
public class FeeController {
    @Autowired
    FeeService feeService;

    @PostMapping
    /* todo con esta anotacion se determina quien esta autorizado a realizar el metodo*/
   // @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    public ResponseEntity addFee(Authentication auth, @RequestBody Fee fee) throws FeeException {
        //Todo de esta manera tambien puedo determinar quien hace que en el metodo.
           String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
               if (role.equals("BACKOFFICE")) {
                   Fee newFee = feeService.add(fee);
                   URI location = ServletUriComponentsBuilder
                           .fromCurrentRequest()
                           .path("/{id}")
                           .buildAndExpand(newFee.getIdFee())
                           .toUri();
                   return ResponseEntity.created(location).build();
               }
               else {
                   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
               }


    }
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @PutMapping("/{id}")
    public ResponseEntity updateFee(@PathVariable Integer id, @RequestBody Fee fee) throws FeeException {
        Fee newFee= feeService.updateFee(id, fee);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newFee.getIdFee())
                .toUri();
        return ResponseEntity.ok(location);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFee(@PathVariable Integer id)
    {
         feeService.deleteFee(id);
         return ResponseEntity.status(HttpStatus.OK).build();
    }


}
