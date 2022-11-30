package com.snapp.boxdemo.mapper;

import com.snapp.boxdemo.model.dto.AddressDto;
import com.snapp.boxdemo.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto addressToAddressDto(Address address);

    Address addressDtoToAddress(AddressDto dto);
}
