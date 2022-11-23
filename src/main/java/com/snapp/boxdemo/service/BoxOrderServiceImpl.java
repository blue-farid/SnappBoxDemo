package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.exception.DuplicateEntityException;
import com.snapp.boxdemo.exception.NotFoundException;
import com.snapp.boxdemo.mapper.BoxOrderMapper;
import com.snapp.boxdemo.repository.BoxOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoxOrderServiceImpl implements BoxOrderService {
    private final BoxOrderMapper mapper = BoxOrderMapper.INSTANCE;
    private final BoxOrderRepository repository;

    private final MessageSource source;

    @Override
    public BoxOrderDto getBoxOrder(long id) {
        BoxOrderDto dto = repository.findById(id).map(mapper::boxOrderToBoxOrderDto).orElse(null);
        if (dto == null)
            throw new NotFoundException(source.getMessage("error.notFound", null, Locale.ENGLISH));
        return dto;
    }

    @Override
    public void removeBoxOrder(long id) {
        if (repository.existsById(id))
            throw new NotFoundException(source.getMessage("error.notFound", null, Locale.ENGLISH));
        repository.deleteById(id);
    }

    @Override
    public BoxOrderDto updateBoxOrder(BoxOrderDto dto) {
        if (repository.existsById(dto.getId()))
            throw new NotFoundException(source.getMessage("error.notFound", null, Locale.ENGLISH));
        return mapper.boxOrderToBoxOrderDto(repository.save(mapper.boxOrderDtoToBoxOrder(dto)));
    }

    @Override
    public BoxOrderDto saveBoxOrder(BoxOrderDto dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new DuplicateEntityException(source.getMessage("error.duplicate", null, Locale.ENGLISH));
        return mapper.boxOrderToBoxOrderDto(repository.save(mapper.boxOrderDtoToBoxOrder(dto)));
    }

    @Override
    public boolean exist(long id) {
        return repository.existsById(id);
    }

    @Override
    public List<BoxOrderDto> getAll() {
        List<BoxOrderDto> orders = new ArrayList<>();
        repository.findAll().forEach(order -> orders.add(mapper.boxOrderToBoxOrderDto(order)));
        return orders;
    }
}
