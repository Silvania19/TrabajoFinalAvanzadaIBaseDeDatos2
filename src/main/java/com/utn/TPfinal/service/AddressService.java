package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Address;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.User;
import com.utn.TPfinal.exception.AddressException;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressDao;
    @Autowired
    ClientService clientService;

    public Address newAddress(Address address) throws FeeException {

            return addressDao.save(address);
    }

    public Address addClientToAddress(Integer id, Integer idClient) throws AddressException {
        Address addressSearch = getByID(id);
        if (addressDao.existsById(addressSearch.getIdAddress())) {
            User client = clientService.getByID(idClient); // nullpointerexception en client
            addressSearch.setClient((Client)client);
            return addressDao.save(addressSearch);
        } else {
            throw new AddressException("Error en addClientToAddress");
        }
    }

    public Address getByID(Integer id) {
        return addressDao.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Address updateAddress(Integer id, Address address) throws AddressException {
        if (addressDao.existsById(id)) {
            Address oldAddress = getByID(id);
            oldAddress.setNameAddress(address.getNameAddress());
            oldAddress.setNumberAddress(address.getNumberAddress());

            Address actualAddress = addressDao.save(oldAddress);
            return actualAddress;
        } else {
            throw new AddressException("Error, el id no existe");
        }
    }

    public void deleteFee(Integer id) {
        if (addressDao.existsById(id) ){
            addressDao.deleteById(id);
        } else {
            throw new FeeException("Error, el id no existe");
        }
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
