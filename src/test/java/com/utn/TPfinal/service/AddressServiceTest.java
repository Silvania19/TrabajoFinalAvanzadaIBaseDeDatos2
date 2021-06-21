package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Address;
import com.utn.TPfinal.exception.AddressException;
import com.utn.TPfinal.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.utn.TPfinal.utils.TestUtils.aAddress;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    AddressService addressService;
    AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        addressRepository = mock(AddressRepository.class);
        addressService = new AddressService(addressRepository);
    }

    @Test
    public void newAddressHappyPathTest() {

        //given
        Address address = aAddress();
        when(addressRepository.save(address)).thenReturn(address);

        //when

        Address addressR = addressService.newAddress(address);

        //then
        assertEquals(addressR.getIdAddress(), address.getIdAddress());
    }

    @Test
    public void updateAddressHappyPathTest(){

        //given
        when(addressRepository.findById(aAddress().getIdAddress())).thenReturn(Optional.of(aAddress()));
        Address nAddress = aAddress();
        nAddress.setNameAddress("newNameAddress");
        when(addressRepository.save(nAddress)).thenReturn(nAddress);

        //when
        Address addressUpdated = addressService.updateAddress(aAddress().getIdAddress(), nAddress);

        //then
        assertEquals(addressUpdated.getNameAddress(), nAddress.getNameAddress());
    }

    @Test
    public void updateAddressExceptionTest(){

        try{
            //given
            when(addressRepository.findById(2)).thenReturn(Optional.of(aAddress()));
            Address nAddress = aAddress();
            nAddress.setNameAddress("newNameAddress");
            when(addressRepository.save(nAddress)).thenReturn(nAddress);

            //when
            Address addressUpdated = addressService.updateAddress(aAddress().getIdAddress(), nAddress);

        }catch (AddressException e){

        }
    }

    @Test
    public void deleteAddressHappyPathTest(){

        //given
        Address address = aAddress();
        when(addressRepository.findById(address.getIdAddress())).thenReturn(Optional.of(address));

        //when
        addressService.deleteAddress(address.getIdAddress());

        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    public void deleteAddressExceptionTest(){

        try{
            //given
            when(addressRepository.findById(aAddress().getIdAddress())).thenReturn(Optional.of(aAddress()));

            //when
            addressService.deleteAddress(5);

            verify(addressRepository, times(1)).delete(aAddress());

        }catch(AddressException e){

        }
    }


}