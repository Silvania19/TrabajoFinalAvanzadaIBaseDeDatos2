package com.utn.TPfinal.controller;
import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.exception.NotFoundException;

import com.utn.TPfinal.service.FeeService;
import com.utn.TPfinal.util.EntityURLBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/fees")
public class FeeController {

    FeeService feeService;

    @Autowired
    public FeeController(FeeService feeService){
        this.feeService = feeService;
    }

    @PostMapping
    /* con esta anotacion se determina quien esta autorizado a realizar el metodo*/
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    public ResponseEntity addFee(/*Authentication auth,*/ @RequestBody Fee fee) throws FeeException {
        //de esta manera tambien puedo determinar quien hace que en el metodo.
          /* String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
               if (role.equals("BACKOFFICE")) {*/
                   Fee newFee = feeService.add(fee);
                   URI location = EntityURLBuilder.buildURL("fee", newFee.getIdFee());
                   return ResponseEntity.created(location).build();
              /* }
               else {
                   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
               }*/

    }

    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @PutMapping("/{id}")
    public ResponseEntity updateFee(@PathVariable Integer id, @RequestBody Fee fee) throws NotFoundException {
        Fee newFee= feeService.updateFee(id, fee);
        URI location= EntityURLBuilder.buildURL("fee", newFee.getIdFee());
        return ResponseEntity.ok(location);
    }

    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFee(@PathVariable Integer id)
    {
         feeService.deleteFee(id);
         return ResponseEntity.status(HttpStatus.OK).build();
    }


}
