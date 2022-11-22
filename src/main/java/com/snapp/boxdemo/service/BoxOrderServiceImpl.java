package com.snapp.boxdemo.service;

import com.snapp.boxdemo.dto.BoxOrderDto;
import com.snapp.boxdemo.mapper.BoxOrderMapper;
import com.snapp.boxdemo.model.BoxOrder;
import com.snapp.boxdemo.repository.BoxOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxOrderServiceImpl implements BoxOrderService {
    private final BoxOrderMapper mapper = BoxOrderMapper.INSTANCE;
    private final BoxOrderRepository repository;

    @Override
    public BoxOrderDto getBoxOrder(long id) {
        return repository.findById(id).map(mapper::boxOrderToBoxOrderDto).orElse(null);
    }

    @Override
    public void removeBoxOrder(long id) {
        repository.deleteById(id);
    }

    @Override
    public void saveOrUpdateBoxOrder(BoxOrderDto dto) {
        repository.save(mapper.boxOrderDtoToBoxOrder(dto));
    }

    @Override
    public boolean exist(long id) {
        return repository.existsById(id);
    }

    @Override
    public Iterable<BoxOrderDto> getAll() {
        Iterable<BoxOrder> orders = repository.findAll();
        List<BoxOrderDto> dtos = new ArrayList<>();
        for (BoxOrder order : orders) {
            dtos.add(mapper.boxOrderToBoxOrderDto(order));
        }
        return dtos;
    }
}
