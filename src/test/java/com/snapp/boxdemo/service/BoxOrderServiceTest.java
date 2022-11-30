package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.dto.DestinationNodeDto;
import com.snapp.boxdemo.model.entity.OrderType;
import com.snapp.boxdemo.model.entity.PriceRange;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;
import com.snapp.boxdemo.repository.BoxOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BoxOrderServiceTest {

    @Autowired
    BoxOrderService service;

    @Autowired
    BoxOrderRepository repository;

    @Test
    void getBoxOrder() {
        BoxOrderDto dto = service.getBoxOrder(1L);
        assertEquals(1L, dto.getId());
        assertEquals(1L, dto.getOwnerId());
        assertEquals("sample comment", dto.getSourceComment());
        assertEquals("09339018022", dto.getSourcePhoneNumber());
        assertEquals("Farid Masjedi", dto.getSourceFullName());
        assertEquals("Neda Masjedi", dto.getDestinations().get(0).getFullName());
        assertEquals("09123456789", dto.getDestinations().get(0).getPhoneNumber());
        assertEquals("Tehran", dto.getSourceAddressBase());
        assertEquals("Tehran", dto.getDestinations().get(0).getAddressBase());
        assertEquals("1", dto.getSourceAddressHomeUnit());
        assertEquals("1", dto.getDestinations().get(0).getAddressHomeUnit());
        assertEquals("1", dto.getDestinations().get(0).getAddressHouseNumber());
        assertEquals("1", dto.getSourceAddressHouseNumber());
        assertEquals(PriceRange.UP_TO_ONE, dto.getDestinations().get(0).getPriceRange());
        assertEquals(OrderType.BIKE, dto.getOrderType());
    }

    @Test
    void removeBoxOrder() {
        long id = 1;
        assertTrue(repository.existsById(id));
        service.removeBoxOrder(id);
        assertFalse(repository.existsById(id));
    }

    @Test
    void updateBoxOrder() {
        String newSourceComment = "new sample comment";
        long id = 1L;
        assertTrue(repository.existsById(id));
        BoxOrderDto dto = service.getBoxOrder(id);
        assertNotEquals(newSourceComment, dto.getSourceComment());
        dto.setSourceComment(newSourceComment);
        service.saveBoxOrder(dto);
        dto = service.getBoxOrder(id);
        assertEquals(newSourceComment, dto.getSourceComment());
    }

    @Test
    void saveBoxOrder() {
        long ownerId = 1;
        String comment = "new comment";
        String addressBase = "new address base";
        String addressHomeUnit = "2";
        String addressHouseNumber = "2";
        String phoneNumber = "09123456789";
        String fullName = "new full name";
        OrderType orderType = OrderType.CAR;
        PriceRange priceRange = PriceRange.UP_TO_ONE;
        List<DestinationNodeDto> destinationNodeDtoList = new ArrayList<>();
        DestinationNodeDto destinationNodeDto = new DestinationNodeDto();
        destinationNodeDto.setAddressHouseNumber(addressHouseNumber);
        destinationNodeDto.setPhoneNumber(phoneNumber);
        destinationNodeDto.setComment(comment);
        destinationNodeDto.setPriceRange(priceRange);
        destinationNodeDto.setAddressHomeUnit(addressHomeUnit);
        destinationNodeDto.setFullName(fullName);
        destinationNodeDto.setAddressBase(addressBase);
        destinationNodeDtoList.add(destinationNodeDto);
        BoxOrderDto dto = new BoxOrderDto();
        dto.setOrderType(orderType);
        dto.setOwnerId(ownerId);
        dto.setSourcePhoneNumber(phoneNumber);
        dto.setSourceComment(comment);
        dto.setSourceAddressBase(addressBase);
        dto.setSourceAddressHomeUnit(addressHomeUnit);
        dto.setSourceAddressHouseNumber(addressHouseNumber);
        dto.setDestinations(destinationNodeDtoList);
        dto.setOrderType(orderType);
        dto.setSourceFullName(fullName);
        dto = service.saveBoxOrder(dto);
        assertTrue(repository.existsById(dto.getId()));
        assertEquals(ownerId, dto.getOwnerId());
        assertEquals(comment, dto.getSourceComment());
        assertEquals(phoneNumber, dto.getSourcePhoneNumber());
        assertEquals(fullName, dto.getSourceFullName());
        assertEquals(fullName, dto.getDestinations().get(0).getFullName());
        assertEquals(phoneNumber, dto.getDestinations().get(0).getPhoneNumber());
        assertEquals(addressBase, dto.getSourceAddressBase());
        assertEquals(addressBase, dto.getDestinations().get(0).getAddressBase());
        assertEquals(addressHomeUnit, dto.getSourceAddressHomeUnit());
        assertEquals(addressHomeUnit, dto.getDestinations().get(0).getAddressHomeUnit());
        assertEquals(addressHouseNumber, dto.getDestinations().get(0).getAddressHouseNumber());
        assertEquals(addressHouseNumber, dto.getSourceAddressHouseNumber());
        assertEquals(orderType, dto.getOrderType());
        assertEquals(priceRange, dto.getDestinations().get(0).getPriceRange());
        repository.deleteById(dto.getId());
        assertFalse(repository.existsById(dto.getId()));
    }

    @Test
    void exist() {
        long id = 1;
        assertEquals(repository.existsById(id), service.exist(id));
    }

    @Test
    void getAll() {
        List<BoxOrderDto> dto = service.getAll();
        assertNotNull(dto);
        assertEquals(1, dto.size());
    }

    @Test
    void searchBoxOrders() {
        BoxOrderSearchWrapper wrapper = BoxOrderSearchWrapper.builder()
                .ownerId(1L).build();

        List<BoxOrderDto> list = service.searchBoxOrders(wrapper, 1);
        assertEquals(1, list.get(0).getOwnerId());
        assertEquals(OrderType.BIKE, list.get(0).getOrderType());
        list = service.searchBoxOrders(wrapper, 2);
        assertEquals(OrderType.CAR, list.get(0).getOrderType());
        wrapper.setOwnerId(2L);
        list = service.searchBoxOrders(wrapper, 1);
        assertEquals(0, list.size());
    }
}