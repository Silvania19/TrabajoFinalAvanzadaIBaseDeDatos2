package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.projections.Consumption;
import com.utn.TPfinal.service.BillService;
import com.utn.TPfinal.service.ClientService;
import com.utn.TPfinal.service.MeasuringService;
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
import java.util.stream.Collectors;

import static com.utn.TPfinal.util.ResponseEntityList.responseList;

@RestController
@RequestMapping("/clients")
public class ClientController {

    ClientService clientService;
    ModelMapper modelMapper;
    BillService billService;
    MeasuringService measuringService;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper modelMapper, BillService billService, MeasuringService measuringService) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
        this.billService = billService;
        this.measuringService = measuringService;
    }


    /*@PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")*/
    /**Client Api**/


    //android - 2) Consulta de facturas por rango de fechas.
    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @GetMapping("{idClient}/bills")
    public ResponseEntity<List<Bill>>getBillsByRangeOfDatesByUser(@PathVariable Integer idClient,
                                                                  Authentication authentication,
                                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate,
                                                                  Pageable pageable){
        UserDto client = modelMapper.map(authentication.getPrincipal(), UserDto.class);
        if(client != null && idClient == client.getId()){
            Page pageOfBills = billService.getBillsByUserAndDateBetween(client.getId(), beginDate, endDate, pageable);
            return ResponseEntityList.responsePage(pageOfBills);
        }else{
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //3) client Consulta de deuda (Facturas impagas)

    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @GetMapping("{idClient}/bills/unpaid")
    public ResponseEntity<List<Bill>> billsNotPay(@PathVariable Integer idClient,
                                                  Authentication authentication) {
        Client client = modelMapper.map(authentication.getPrincipal(), Client.class);

        //en el caso de que el client=null. no found, y en el otro unauthorized
        if (client != null && idClient == client.getId()) {
            List<Bill> bills = billService.getBillsByIdClientNotPay(client.getId());

            return responseList(bills);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /*4) Consulta de consumo por rango de fechas (el usuario va a ingresar un rango
         de fechas y quiere saber cuánto consumió en ese periodo en Kwh y dinero) */

    @PreAuthorize(value = "hasAnyAuthority('CLIENT')")
    @GetMapping("{idClient}/consumption")
    public ResponseEntity consumptionRangeDateKwMoney(@PathVariable Integer idClient,
                                                      Authentication authentication,
                                                      @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                      @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate) {
        UserDto userDto= modelMapper.map(authentication.getPrincipal(), UserDto.class);
        Consumption consumption= measuringService.consumption(userDto.getId(), beginDate, endDate);
        if(consumption!= null && idClient == userDto.getId()){
            return ResponseEntity.ok(consumption);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /* client 5) Consulta de mediciones por rango de fechas */

    @PreAuthorize(value = "hasAnyAuthority('CLIENT')")
    @GetMapping("{idClient}/measurings")
    public ResponseEntity<List<Measuring>>getMeasuringsByRangeOfDates(@PathVariable Integer idClient,
                                                                      Authentication authentication,
                                                                      @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                                      @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate,
                                                                      Pageable pageable){
        UserDto client = modelMapper.map(authentication.getPrincipal(), UserDto.class);
        if(client != null && idClient == client.getId()) {
            Page pageOfMeasurings = measuringService.findMeasuringsByRangeOfDatesAndClient(client.getId(), beginDate, endDate, pageable);
            return ResponseEntityList.responsePage(pageOfMeasurings);
        }else{
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } // si el cliente es igual a null no deberia traer un conflict ?
    }

    /**Backoffice **/

    /* 4) Consulta de facturas impagas por cliente y domicilio.*/
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @GetMapping("{idClient}/address/{idAddress}/bills/unpaid")
    public ResponseEntity<List<Bill>>getUnpaidBillsByClientAndAddress(@PathVariable Integer idClient,
                                                                      @PathVariable Integer idAddress,
                                                                      Pageable pageable){
        Page pageOfBills = billService.getUnpaidBillsByClientIdAndAddressId(idClient, idAddress, pageable);
        // en caso de no encontrar o bien la direccion, o el cliente un no found,(hoy devuelve no content)
        return ResponseEntityList.responsePage(pageOfBills);
    }

   //5) Consulta 10 clientes más consumidores en un rango de fechas
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @GetMapping("moreConsumers")
    public ResponseEntity<List<UserDto>> moreConsumersOfDateRange(@RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                                  @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate){

        List<UserDto> userList = clientService.tenMoreConsumers(beginDate, endDate).
                stream().map(o -> modelMapper.map(o, UserDto.class)).collect(Collectors.toList());

        return ResponseEntityList.responseList(userList);
    }

}