package com.snapp.boxdemo.service;

import com.snapp.boxdemo.mapper.AddressMapper;
import com.snapp.boxdemo.model.dto.AddressDto;
import com.snapp.boxdemo.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final AddressMapper mapper = AddressMapper.INSTANCE;

    @Override
    public AddressDto getAddress(long id) {
        return repository.findById(id).map(mapper::addressToAddressDto).orElse(null);
    }

    @Override
    public void removeAddress(long id) {
        repository.deleteById(id);
    }

    @Override
    public AddressDto updateAddress(AddressDto dto) {
        return mapper.addressToAddressDto(repository.save(mapper.addressDtoToAddress(dto)));
    }

    @Override
    public AddressDto saveAddress(AddressDto dto) {
        return mapper.addressToAddressDto(repository.save(mapper.addressDtoToAddress(dto)));
    }

    @Override
    public boolean exist(long id) {
        return repository.existsById(id);
    }

}
