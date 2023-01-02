package com.snapp.boxdemo.mapper;

import com.snapp.boxdemo.model.dto.ClientDto;
import com.snapp.boxdemo.model.entity.Client;
import com.snapp.boxdemo.model.security.ClientUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto clientToClientDto(Client client);

    Client clientDtoToClient(ClientDto dto);

    @Mapping(target = "authorities", source = "roles")
    @Mapping(target = "username", source = "id")
    @Mapping(target = "password", source = "oneTimePassword")
    @Mapping(target = "accountNonExpired", constant = "true")
    @Mapping(target = "accountNonLocked", constant = "true")
    @Mapping(target = "credentialsNonExpired", constant = "true")
    @Mapping(target = "enabled", constant = "true")
    ClientUserDetails clientToClientUserDetails(Client client);
}
