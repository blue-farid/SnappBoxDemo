package com.snapp.boxdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.snapp.boxdemo.mapper.BoxOrderMapper;
import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.dto.DestinationNodeDto;
import com.snapp.boxdemo.model.entity.BoxOrder;
import com.snapp.boxdemo.model.entity.OrderType;
import com.snapp.boxdemo.model.entity.PriceRange;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;
import com.snapp.boxdemo.service.BoxOrderService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class BoxOrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoxOrderService boxOrderService;

    @Autowired
    MessageSource messageSource;

    Locale locale = Locale.ENGLISH;

    static BoxOrderMapper boxOrderMapper = BoxOrderMapper.INSTANCE;

    @Test
    @SneakyThrows
    void getBoxOrder_ok() {
        // given
        given(boxOrderService.getBoxOrder(anyLong())).willReturn(boxOrderMapper.boxOrderToBoxOrderDto(BoxOrder.builder().id(1L).build()));

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
        destinationNodeDto.setX(2.0);
        destinationNodeDto.setY(2.0);
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
        order0.setSourceX(1.0);
        order0.setSourceY(1.0);

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
        destinationNodeDto.setX(2.0);
        destinationNodeDto.setY(2.0);
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
        order0.setSourceX(1.0);
        order0.setSourceY(1.0);

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