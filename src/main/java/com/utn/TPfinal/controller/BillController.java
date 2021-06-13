package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.BillService;
import com.utn.TPfinal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class BillController {

    BillService billService;

    @Autowired
    public BillController(BillService billService){
        this.billService = billService;
    }

    //android - 2) Consulta de facturas por rango de fechas.
    //client/1/fecha.
    //backoffice/clients/1/rangodefecha portal del usuario
    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @GetMapping("clientApi/client/{idClient}")
    public ResponseEntity<List<Bill>>getBillsByRangeOfDatesByUser(Authentication authentication,
                                                                  @PathVariable Integer idClient,
                                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate,
                                                                  Pageable pageable){
        UserDto userDto = (UserDto) authentication.getPrincipal();
        if(userDto.getId() == idClient){
            Page pageOfBills = billService.getBillsByUserAndDateBetween(idClient, beginDate, endDate, pageable);
            return response(pageOfBills);
        }else{
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    /** backoffice 4) Consulta de facturas impagas por cliente y domicilio.**/
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @GetMapping("client/{idClient}/{idDomicilio}")
    public List<Bill>getBills(@PathVariable Integer idClient, @PathVariable Integer idDomicilio){
        return billService.getAllBillsByIdClient(idClient, idDomicilio);
    }
}
