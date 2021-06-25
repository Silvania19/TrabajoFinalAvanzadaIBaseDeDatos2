package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Address;
import com.utn.TPfinal.exception.AddressException;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public Address newAddress(Address address) throws AddressException {
            return addressRepository.save(address);
    }

    public Address updateAddress(Integer id, Address address) throws AddressException {

        Optional<Address> addressOld = addressRepository.findById(id);
        if(addressOld.isPresent()){
            addressOld.get().setNameAddress(address.getNameAddress());
            addressOld.get().setNumberAddress(address.getNumberAddress());

        Address actualAddress = addressRepository.save(addressOld.get());
        return actualAddress;

        } else {
            throw new AddressException("Error, el id no existe");
        }
    }

    public void deleteAddress(Integer id) {
        Optional<Address> dAddress = addressRepository.findById(id);
        if (dAddress.isPresent()){
            addressRepository.delete(dAddress.get());
        } else {
            throw new AddressException("Error, el id no existe");
        }
    }
}
