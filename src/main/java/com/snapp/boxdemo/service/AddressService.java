package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.dto.AddressDto;

public interface AddressService {

    AddressDto getAddress(long id);

    void removeAddress(long id);

    AddressDto updateAddress(AddressDto dto);

    AddressDto saveAddress(AddressDto dto);

    boolean exist(long id);
}
