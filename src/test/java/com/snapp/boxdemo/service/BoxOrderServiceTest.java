package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.entity.*;
import com.snapp.boxdemo.model.entity.node.DestinationNode;
import com.snapp.boxdemo.model.entity.node.SourceNode;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;
import com.snapp.boxdemo.repository.BoxOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
class BoxOrderServiceTest {

    @Autowired
    BoxOrderService service;

    @MockBean
    BoxOrderRepository boxOrderRepository;

    @Test
    void getBoxOrder_ok() {
        // given
        long id = 1L;
        SourceNode sourceNode = new SourceNode();
        sourceNode.setAddress(Address.builder()
                        .base("sample address base")
                        .homeUnit("1")
                        .houseNumber("1")
                        .build());
        sourceNode.setComment("sample source comment");
        sourceNode.setFullName("Farid Masjedi");
        sourceNode.setPhoneNumber("09123456789");

        DestinationNode destinationNode = new DestinationNode();
        destinationNode.setFullName("Neda Masjedi");
        destinationNode.setComment("sample destination comment");
        destinationNode.setPhoneNumber("09123456789");
        destinationNode.setPriceRange(PriceRange.UP_TO_ONE);
        destinationNode.setAddress(Address.builder()
                        .base("sample address base")
                        .houseNumber("1")
                        .homeUnit("1")
                        .build());

        List<DestinationNode> destinations = new ArrayList<>();
        destinations.add(destinationNode);

        given(boxOrderRepository.findById(id)).willReturn(Optional.of(BoxOrder.builder()
                        .id(id)
                        .owner(Client.builder().id(id).build())
                        .orderType(OrderType.BIKE)
                        .source(sourceNode)
                        .destinations(destinations)
                        .build()));

        // when
        BoxOrderDto dto = service.getBoxOrder(id);

        //then
        assertEquals(id, dto.getId());
        assertEquals(id, dto.getOwnerId());
        assertEquals("sample source comment", dto.getSourceComment());
        assertEquals("09123456789", dto.getSourcePhoneNumber());
        assertEquals("Farid Masjedi", dto.getSourceFullName());
        assertEquals("Neda Masjedi", dto.getDestinations().get(0).getFullName());
        assertEquals("09123456789", dto.getDestinations().get(0).getPhoneNumber());
        assertEquals("sample address base", dto.getSourceAddressBase());
        assertEquals("sample address base", dto.getDestinations().get(0).getAddressBase());
        assertEquals("1", dto.getSourceAddressHomeUnit());
        assertEquals("1", dto.getDestinations().get(0).getAddressHomeUnit());
        assertEquals("1", dto.getDestinations().get(0).getAddressHouseNumber());
        assertEquals("1", dto.getSourceAddressHouseNumber());
        assertEquals(PriceRange.UP_TO_ONE, dto.getDestinations().get(0).getPriceRange());
        assertEquals(OrderType.BIKE, dto.getOrderType());
    }

    @Test
    void removeBoxOrder_ok() {
        //given
        long id = 1;

        given(boxOrderRepository.existsById(id)).willReturn(true);

        //when then
        service.removeBoxOrder(id);
    }

    @Test
    void updateBoxOrder_ok() {
        //given
        long orderId = 1L;
        long clientId = 1L;
        long newClientId = 2L;
        given(boxOrderRepository.findById(orderId)).willReturn(Optional.of(BoxOrder.builder().id(orderId).
                owner(Client.builder().id(clientId).build()).build()));

        given(boxOrderRepository.save(any(BoxOrder.class))).willReturn(BoxOrder.builder().id(orderId).
                owner(Client.builder().id(newClientId).build()).build());

        given(boxOrderRepository.existsById(orderId)).willReturn(true);

        //when
        BoxOrderDto dto = service.getBoxOrder(orderId);
        assertNotEquals(newClientId, dto.getOwnerId());
        dto.setOwnerId(newClientId);
        dto = service.updateBoxOrder(dto);

        //then
        assertEquals(newClientId, dto.getOwnerId());
    }

    @Test
    void saveBoxOrder_ok() {
        //given
        long orderId = 1L;

        given(boxOrderRepository.save(any(BoxOrder.class))).willReturn(BoxOrder.builder().id(orderId).build());

        given(boxOrderRepository.existsById(orderId)).willReturn(false);

        BoxOrderDto order0 = new BoxOrderDto();
        order0.setId(orderId);

        //when
        BoxOrderDto dto = service.saveBoxOrder(order0);

        //then
        assertEquals(orderId, dto.getId());
    }

    @Test
    void exist_ok() {
        //given
        long id = 1;
        given(boxOrderRepository.existsById(id)).willReturn(true);

        //when then
        assertTrue(service.exist(id));
    }

    @Test
    void getAll_ok() {
        //given
        List<BoxOrder> orders = new ArrayList<>();
        orders.add(BoxOrder.builder().id(1L).build());
        orders.add(BoxOrder.builder().id(2L).build());
        given(boxOrderRepository.findAll()).willReturn(orders);

        //when
        List<BoxOrderDto> dto = service.getAll();

        //then
        assertNotNull(dto);
        assertEquals(2, dto.size());
        assertEquals(1L, dto.get(0).getId());
        assertEquals(2L, dto.get(1).getId());
    }

    @Test
    void searchBoxOrders_ok() {
        //given
        BoxOrderSearchWrapper wrapper = BoxOrderSearchWrapper.builder()
                .orderType(OrderType.BIKE).build();

        List<BoxOrder> orders = new ArrayList<>();
        orders.add(BoxOrder.builder().id(1L).orderType(OrderType.BIKE).build());
        orders.add(BoxOrder.builder().id(2L).orderType(OrderType.BIKE).build());

        given(boxOrderRepository.findAll(any(Example.class), any(Pageable.class))).willReturn(
                new PageImpl(orders)
        );

        //when
        List<BoxOrderDto> page0 = service.searchBoxOrders(wrapper, 0);

        //then
        assertNotNull(page0);
        assertEquals(1L, page0.get(0).getId());
        assertEquals(2L, page0.get(1).getId());
    }
}