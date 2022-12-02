package com.snapp.boxdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.snapp.boxdemo.mapper.BoxOrderMapper;
import com.snapp.boxdemo.mapper.ClientMapper;
import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.dto.ClientDto;
import com.snapp.boxdemo.model.dto.DestinationNodeDto;
import com.snapp.boxdemo.model.entity.BoxOrder;
import com.snapp.boxdemo.model.entity.OrderType;
import com.snapp.boxdemo.model.entity.PriceRange;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;
import com.snapp.boxdemo.repository.BoxOrderRepository;
import com.snapp.boxdemo.repository.ClientRepository;
import com.snapp.boxdemo.service.BoxOrderService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EndToEndTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BoxOrderService boxOrderService;

    @Autowired
    MessageSource messageSource;

    Locale locale = Locale.ENGLISH;

    static BoxOrderMapper boxOrderMapper = BoxOrderMapper.INSTANCE;

    static ClientMapper clientMapper = ClientMapper.INSTANCE;

    static BoxOrderMapper orderMapper = BoxOrderMapper.INSTANCE;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BoxOrderRepository boxOrderRepository;

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
    @SneakyThrows
    void getBoxOrder_ok() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order/1")
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON);

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("get.success", null, locale)))
                .andExpect(jsonPath("$.result.id").value(1L));
    }

    @Test
    @SneakyThrows
    void postBoxOrder_ok() {
        // given
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

        given(boxOrderService.saveBoxOrder(any(BoxOrderDto.class))).willReturn(boxOrderMapper.boxOrderToBoxOrderDto(BoxOrder.builder()
                .id(order0.getId()).build()));

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order")
                .build();

        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(order0);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("save.success", null, locale)))
                .andExpect(jsonPath("$.result.id").value(1L));
    }

    @Test
    @SneakyThrows
    void putBoxOrder_ok() {
        // given
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

        given(boxOrderService.updateBoxOrder(any(BoxOrderDto.class))).willReturn(boxOrderMapper.boxOrderToBoxOrderDto(BoxOrder.builder()
                .id(order0.getId()).build()));

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order")
                .build();

        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(order0);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("update.success", null, locale)))
                .andExpect(jsonPath("$.result.id").value(1L));
    }

    @Test
    @SneakyThrows
    void deleteBoxOrder_ok() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order/1")
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON);

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("remove.success", null, locale)))
                .andExpect(jsonPath("$.result").isEmpty());
    }

    @Test
    @SneakyThrows
    void searchBoxOrder_ok() {
        // given
        BoxOrderSearchWrapper wrapper = BoxOrderSearchWrapper.builder()
                .ownerId(1L)
                .orderType(OrderType.BIKE).build();

        given(boxOrderService.searchBoxOrders(wrapper, 0)).willReturn(new ArrayList<>());

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order")
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("ownerId", "1")
                .queryParam("orderType", "BIKE")
                .queryParam("page", "0");

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("search.success", null, locale)))
                .andExpect(jsonPath("$.result").isArray());
    }
}
