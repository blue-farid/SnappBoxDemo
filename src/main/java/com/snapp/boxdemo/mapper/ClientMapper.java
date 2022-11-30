package com.snapp.boxdemo.mapper;

import com.snapp.boxdemo.model.dto.ClientDto;
import com.snapp.boxdemo.model.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto clientToClientDto(Client client);

    Client clientDtoToClient(ClientDto dto);
}
