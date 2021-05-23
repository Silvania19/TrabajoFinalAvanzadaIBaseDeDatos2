package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Address;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.Person;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.persistence.AddressDao;
import com.utn.TPfinal.persistence.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AddressService {

    @Autowired
    AddressDao addressDao;

    PersonService personService;

    public Address newAddress(Address address) throws FeeException {
        if (!addressDao.existsById(address.getId_address())) {
            return addressDao.save(address);
        } else {
            throw new FeeException("Error address service");
        }
    }

    public Address addClientToAddress(Integer id, Integer idClient) throws FeeException {
        Address addressSearch = getByID(id);
        if (addressDao.existsById(addressSearch.getId_address())) {
            System.out.println("asdasd");
            Person client = personService.getByID(idClient); // nullpointerexception en client
            System.out.println(client);
            addressSearch.setClient((Client)client);

            return addressDao.save(addressSearch);
        } else {
            throw new FeeException("Error en addClientToAddress");
        }
    }

    public Address getByID(Integer id) {
        return addressDao.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}

/*
    public Address newAddress(Address address, Integer idClient) throws FeeException {
        if (!addressDao.existsById(address.getId_address())) {

            Person client = personService.getByID(idClient);
            Address newAddress = addressDao.save(address);
            newAddress.setClient((Client)client);
            return newAddress;
        } else {
            throw new FeeException("Error");
        }
    }
}
*/
