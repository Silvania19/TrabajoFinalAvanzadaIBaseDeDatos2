package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.BillService;
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
@RequestMapping("/bills")
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



    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }


    /**Backoffice **/

    /* backoffice 4) Consulta de facturas impagas por cliente y domicilio.*/
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @GetMapping("client/idClient/{idClient}/idAddress/{idAddress}")
    public List<Bill>getUnpaidBillsByClientAndAddress(@PathVariable Integer idClient, @PathVariable Integer idAddress){
        return billService.findUnpaidBillsByClientIdAndAddressId(idClient, idAddress);
    }


}
