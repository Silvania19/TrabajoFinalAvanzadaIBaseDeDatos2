package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {

    BillService billService;

    @Autowired
    public BillController(BillService billService){
        this.billService = billService;
    }

    //2) Consulta de facturas por rango de fechas.
    //client/1/fecha.
    //backoffice/clients/1/rangodefecha portal del usuario
    @GetMapping()
    public ResponseEntity<List<Bill>> getBillsByRangeOfDates(@RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                             @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate,
                                                             Pageable pageable){
        Page pageOfBills = billService.getBillsByRangeOfDates(beginDate, endDate, pageable);
        return response(pageOfBills);
    }

    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    /**Consulta de facturas impagas por cliente y domicilio.**/
    @GetMapping("/client/{idClient}")
    public List<Bill>getBills(@PathVariable Integer idClient){
        List<Bill> bills= billService.getAllBillsByIdClient(idClient);
        System.out.print(bills);
        return  billService.getAllBillsByIdClient(idClient);
    }
}
