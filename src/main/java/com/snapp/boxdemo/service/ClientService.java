package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.dto.ClientDto;

public interface ClientService {

    ClientDto getClient(long id);

    void removeClient(long id);

    ClientDto updateClient(ClientDto dto);

    ClientDto saveClient(ClientDto dto);

    boolean exist(long id);
}
