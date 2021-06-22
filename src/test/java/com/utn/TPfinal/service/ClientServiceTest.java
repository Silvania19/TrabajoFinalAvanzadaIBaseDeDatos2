package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.Query;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.utn.TPfinal.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    ClientService clientService;
    ClientRepository clientRepository;

    @BeforeEach
    public void setUp(){
        clientRepository= mock(ClientRepository.class);
        clientService= new ClientService(clientRepository);
    }
    @Test
    public void findByNameAndPasswordOk() {
        //given
        String name="lucas";
        String password="1234";
        when(clientRepository.findByNameAndPassword(name, password)).thenReturn(aClient2());

        //when
        Client client= clientService.findByNameAndPassword(name, password);

        //then
        assertEquals(client.getName(), name);
        assertEquals(client.getPassword(), password);
    }
    @Test
    public void tenMoreConsumersOk() throws ParseException {
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        when(clientRepository.tenMoreConsumers(beginDate, endDate)).thenReturn(aListUser());
        List<Client> clients= clientService.tenMoreConsumers(beginDate, endDate);
        assertEquals(clients, aListUser());
    }

}
