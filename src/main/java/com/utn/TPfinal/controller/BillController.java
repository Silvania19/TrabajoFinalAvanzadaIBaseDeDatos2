package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillService billService;

    /*@GetMapping("/{beginDate}/{endDate}")
    public ResponseEntity getBillsByRangeOfDates(@PathVariable Date beginDate, @PathVariable Date endDate)
    {
        List<Bill> billList = billService.getBillsByRangeOfDates(beginDate, endDate);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newFee.getId_fee())
                .toUri();
        return ResponseEntity.ok(location);
        // que hago aca ?
    }*/

    // tendra que devolver un nocontent en caso de no tener info
    @GetMapping()
    public List<Bill> getBillsByRangeOfDates(@RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                             @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate)
    {
        return billService.getBillsByRangeOfDates(beginDate, endDate);
    }
}
