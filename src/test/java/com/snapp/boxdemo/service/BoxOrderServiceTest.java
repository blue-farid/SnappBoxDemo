package com.snapp.boxdemo.service;

import com.snapp.boxdemo.mapper.BoxOrderMapper;
import com.snapp.boxdemo.mapper.ClientMapper;
import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.dto.ClientDto;
import com.snapp.boxdemo.model.dto.DestinationNodeDto;
import com.snapp.boxdemo.model.entity.OrderType;
import com.snapp.boxdemo.model.entity.PriceRange;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;
import com.snapp.boxdemo.repository.BoxOrderRepository;
import com.snapp.boxdemo.repository.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BoxOrderServiceTest {

    @Autowired
    BoxOrderService service;

    @Autowired
    BoxOrderRepository boxOrderRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    static BoxOrderMapper orderMapper = BoxOrderMapper.INSTANCE;
    static ClientMapper clientMapper = ClientMapper.INSTANCE;


    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        // client
        ClientDto client0 = new ClientDto();
        client0.setId(1L);
        client0.setEmail("test@test.com");
        client0.setFullName("Farid Masjedi");
        client0.setPhoneNumber("09123456789");
        clientRepository.save(clientMapper.clientDtoToClient(client0));
        ClientDto client1 = new ClientDto();
        client1.setId(2L);
        client1.setEmail("test@test.com");
        client1.setFullName("Negar Masjedi");
        client1.setPhoneNumber("09111111111");
        clientRepository.save(clientMapper.clientDtoToClient(client1));
        // destination node
        DestinationNodeDto destinationNodeDto = new DestinationNodeDto();
        destinationNodeDto.setId(1L);
        destinationNodeDto.setComment("sample destination comment");
        destinationNodeDto.setFullName("Neda Masjedi");
        destinationNodeDto.setAddressBase("sample address base");
        destinationNodeDto.setPriceRange(PriceRange.UP_TO_ONE);
        destinationNodeDto.setPhoneNumber("09123456789");
        destinationNodeDto.setAddressHomeUnit("1");
        destinationNodeDto.setAddressHouseNumber("1");
        List<DestinationNodeDto> destinationNodeDtoList = new ArrayList<>();
        destinationNodeDtoList.add(destinationNodeDto);

        DestinationNodeDto destinationNodeDto1 = new DestinationNodeDto();
        destinationNodeDto1.setId(2L);
        destinationNodeDto1.setComment("sample destination comment");
        destinationNodeDto1.setFullName("Navid Masjedi");
        destinationNodeDto1.setAddressBase("sample address base");
        destinationNodeDto1.setPriceRange(PriceRange.UP_TO_ONE);
        destinationNodeDto1.setPhoneNumber("09123456789");
        destinationNodeDto1.setAddressHomeUnit("1");
        destinationNodeDto1.setAddressHouseNumber("1");
        List<DestinationNodeDto> destinationNodeDtoList1 = new ArrayList<>();
        destinationNodeDtoList1.add(destinationNodeDto1);
        // box order
        BoxOrderDto order0 = new BoxOrderDto();
        order0.setId(1L);
        order0.setOwnerId(1L);
        order0.setOrderType(OrderType.BIKE);
        order0.setSourceComment("sample source comment");
        order0.setSourcePhoneNumber("09123456789");
        order0.setDestinations(destinationNodeDtoList);
        order0.setSourceFullName("Farid Masjedi");
        order0.setSourceAddressBase("sample address base");
        order0.setSourceAddressHomeUnit("1");
        order0.setSourceAddressHouseNumber("1");
        boxOrderRepository.save(orderMapper.boxOrderDtoToBoxOrder(order0));


        BoxOrderDto order1 = new BoxOrderDto();
        order1.setId(2L);
        order1.setOwnerId(2L);
        order1.setOrderType(OrderType.CAR);
        order1.setSourceComment("sample source comment");
        order1.setSourcePhoneNumber("09123456789");
        order1.setDestinations(destinationNodeDtoList1);
        order1.setSourceFullName("Neda Masjedi");
        order1.setSourceAddressBase("sample address base");
        order1.setSourceAddressHomeUnit("1");
        order1.setSourceAddressHouseNumber("1");
        boxOrderRepository.save(orderMapper.boxOrderDtoToBoxOrder(order1));

        BoxOrderDto order2 = new BoxOrderDto();
        order2.setId(3L);
        order2.setOwnerId(1L);
        order2.setOrderType(OrderType.CAR);
        order2.setSourceComment("sample source comment");
        order2.setSourcePhoneNumber("09123456789");
        order2.setDestinations(destinationNodeDtoList);
        order2.setSourceFullName("Farid Masjedi");
        order2.setSourceAddressBase("sample address base");
        order2.setSourceAddressHomeUnit("1");
        order2.setSourceAddressHouseNumber("1");
        boxOrderRepository.save(orderMapper.boxOrderDtoToBoxOrder(order2));
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @AfterEach
    public void tearDown() {
        boxOrderRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void getBoxOrder_ok() {
        // given
        long id = 1L;

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

        //when
        assertTrue(boxOrderRepository.existsById(id));
        service.removeBoxOrder(id);

        //then
        assertFalse(boxOrderRepository.existsById(id));
    }

    @Test
    void updateBoxOrder_ok() {
        //given
        String newSourceComment = "new sample comment";
        long id = 1L;

        //when
        assertTrue(boxOrderRepository.existsById(id));
        BoxOrderDto dto = service.getBoxOrder(id);
        assertNotEquals(newSourceComment, dto.getSourceComment());
        dto.setSourceComment(newSourceComment);
        dto = service.updateBoxOrder(dto);

        //then
        assertEquals(newSourceComment, dto.getSourceComment());
    }

    @Test
    void saveBoxOrder_ok() {
        //given
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

        //when
        dto = service.saveBoxOrder(dto);

        //then
        assertTrue(boxOrderRepository.existsById(dto.getId()));
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
    }

    @Test
    void exist_ok() {
        //given
        long id = 1;

        //then
        assertEquals(boxOrderRepository.existsById(id), service.exist(id));
    }

    @Test
    void getAll_ok() {
        //when
        List<BoxOrderDto> dto = service.getAll();

        //then
        assertNotNull(dto);
        assertEquals(3, dto.size());
    }

    @Test
    void searchBoxOrders_ok() {
        //given
        BoxOrderSearchWrapper wrapper = BoxOrderSearchWrapper.builder()
                .ownerId(1L).build();

        //when
        List<BoxOrderDto> page0 = service.searchBoxOrders(wrapper, 0);
        List<BoxOrderDto> page1 = service.searchBoxOrders(wrapper, 1);

        //then
        assertNotNull(page0);
        assertNotNull(page1);
        assertEquals(1, page0.get(0).getOwnerId());
        assertEquals(1, page0.size());
        assertEquals(1, page1.size());
    }
}