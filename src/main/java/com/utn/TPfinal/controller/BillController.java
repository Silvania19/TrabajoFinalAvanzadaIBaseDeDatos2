package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.BillService;
import com.utn.TPfinal.util.ResponseEntityList;
import org.modelmapper.ModelMapper;
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
    ModelMapper modelMapper;

    @Autowired
    public BillController(BillService billService, ModelMapper modelMapper){
        this.billService = billService;
        this.modelMapper= modelMapper;
    }


    /*@PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")*/


    /**Client Api**/
    //android - 2) Consulta de facturas por rango de fechas.
    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @GetMapping("clientApi/client")
    public ResponseEntity<List<Bill>>getBillsByRangeOfDatesByUser(Authentication authentication,
                                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate,
                                                                  Pageable pageable){
        UserDto client = modelMapper.map(authentication.getPrincipal(), UserDto.class);
        if(client != null){
            Page pageOfBills = billService.getBillsByUserAndDateBetween(client.getId(), beginDate, endDate, pageable);
            return ResponseEntityList.response(pageOfBills);
        }else{
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //3) client Consulta de deuda (Facturas impagas)

    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @GetMapping("apiClient/unpaid")
    public ResponseEntity<List<Bill>> billsNotPay(Authentication authentication) {
        Client client = modelMapper.map(authentication.getPrincipal(), Client.class);
        if (client != null) {
            List<Bill> bills = billService.getBillsByIdClientNotPay(client.getId());
            HttpStatus httpStatus = bills.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return ResponseEntity.status(httpStatus).body(bills);

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**Backoffice **/

    /* backoffice 4) Consulta de facturas impagas por cliente y domicilio.*/
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @GetMapping("client/idClient/{idClient}/idAddress/{idAddress}")
    public ResponseEntity<List<Bill>>getUnpaidBillsByClientAndAddress(@PathVariable Integer idClient,
                                                                      @PathVariable Integer idAddress,
                                                                      Pageable pageable){
        Page pageOfBills = billService.getUnpaidBillsByClientIdAndAddressId(idClient, idAddress, pageable);
        return ResponseEntityList.response(pageOfBills);
    }

}
