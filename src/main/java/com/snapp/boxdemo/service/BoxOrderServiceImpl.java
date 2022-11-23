package com.snapp.boxdemo.service;

import com.snapp.boxdemo.exception.DuplicateEntityException;
import com.snapp.boxdemo.exception.NotFoundException;
import com.snapp.boxdemo.mapper.BoxOrderMapper;
import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.entity.BoxOrder;
import com.snapp.boxdemo.model.entity.Client;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;
import com.snapp.boxdemo.repository.BoxOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoxOrderServiceImpl implements BoxOrderService {
    private final BoxOrderMapper mapper = BoxOrderMapper.INSTANCE;
    private final BoxOrderRepository repository;

    private final MessageSource source;

    private final Environment env;

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
        return repository.findAll().stream().map(mapper::boxOrderToBoxOrderDto).toList();
    }

    @Override
    public List<BoxOrderDto> searchBoxOrders(BoxOrderSearchWrapper wrapper, int page) {
        Pageable pageable = PageRequest.of(page, Integer.parseInt(
                Objects.requireNonNull(env.getProperty("spring.data.rest.default-page-size")))).
                withSort(Sort.Direction.DESC, "creationDate");

        BoxOrder boxOrder = BoxOrder.builder().owner(
                Client.builder().fullName(wrapper.getOwnerFullName())
                        .id(Long.valueOf(wrapper.getOwnerId()))
                        .phoneNumber(wrapper.getOwnerPhoneNumber()).build())
                .orderType(wrapper.getOrderType()).creationDate(wrapper.getCreationDate()).build();
        return repository.findAll(Example.of(boxOrder), pageable)
                .stream().map(mapper::boxOrderToBoxOrderDto).toList();
    }
}
