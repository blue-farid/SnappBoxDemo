package com.snapp.boxdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.snapp.boxdemo.mapper.BoxOrderMapper;
import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.model.dto.BoxPriceResponseDto;
import com.snapp.boxdemo.model.entity.*;
import com.snapp.boxdemo.model.entity.node.DestinationNode;
import com.snapp.boxdemo.model.entity.node.SourceNode;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;
import com.snapp.boxdemo.repository.BoxOrderRepository;
import com.snapp.boxdemo.repository.ClientRepository;
import com.snapp.boxdemo.repository.NodeRepository;
import com.snapp.boxdemo.service.BoxOrderService;
import com.snapp.boxdemo.service.PricingService;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class EndToEndTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BoxOrderService boxOrderService;

    @Autowired
    MessageSource messageSource;

    Locale locale = Locale.ENGLISH;

    static BoxOrderMapper orderMapper = BoxOrderMapper.INSTANCE;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BoxOrderRepository boxOrderRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    PricingService pricingService;

    @Value("${price.service.port}")
    String pricingPort;

    @Value("${price.service.host}")
    String pricingHost;

    @Value("${price.service.scheme}")
    String pricingScheme;

    long ownerId;

    ObjectWriter jsonWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    MockWebServer pricingServer;

    @SneakyThrows
    @PostConstruct
    void setUp() {
        ownerId = clientRepository.save(Client.builder()
                .fullName("Farid Masjedi")
                .phoneNumber("09123456789")
                .email("test@test.com")
                .id(1L)
                .build()).getId();
    }

    @BeforeEach
    @SneakyThrows
    void priceServerUp() {
        pricingServer = new MockWebServer();
        pricingServer.start(Integer.parseInt(pricingPort));
        // mock response from pricing service
        BoxPriceResponseDto dto = new BoxPriceResponseDto();
        dto.setPriceAmount("40000");
        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(200);
        mockResponse.setBody(jsonWriter.writeValueAsString(BaseResponseDto.<BoxPriceResponseDto>builder().result(dto).message("success").build()));
        mockResponse.addHeader("Content-Type", "application/json");
        pricingServer.enqueue(mockResponse);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        pricingServer.shutdown();
    }

    @Test
    @SneakyThrows
    void givenBoxOrderId_whenGetBoxOrderById_thenReturnBoxOrderObject() {
        // given
        List<DestinationNode> destinationNodes = new ArrayList<>();
        destinationNodes.add(DestinationNode.builder()
                .address(Address.builder()
                        .homeUnit("1")
                        .houseNumber("1")
                        .base("destination address")
                        .build())
                .priceRange(PriceRange.UP_TO_ONE)
                .comment("destination comment")
                .phoneNumber("09123456789")
                .fullName("destination full name")
                .x(2.0)
                .y(2.0)
                .build());

        BoxOrder boxOrder = BoxOrder.builder()
                .orderType(OrderType.BIKE)
                .source(SourceNode.builder()
                        .fullName("test source full name")
                        .phoneNumber("09123456789")
                        .comment("test source comment")
                        .address(Address.builder()
                                .base("test source base")
                                .houseNumber("1")
                                .homeUnit("1")
                                .build())
                        .x(1.0)
                        .y(1.0)
                        .build())
                .owner(Objects.requireNonNull(clientRepository.findById(1L).get()))
                .destinations(destinationNodes)
                .price(40000.0)
                .build();

        long id = boxOrderRepository.save(boxOrder).getId();

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order/{id}")
                .uriVariables(Map.of("id", id))
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON);

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("get.success", null, locale)))
                .andExpect(jsonPath("$.result.id").value(id))
                .andExpect(jsonPath("$.result.orderType").value("BIKE"))
                .andExpect(jsonPath("$.result.sourceFullName").value("test source full name"))
                .andExpect(jsonPath("$.result.sourcePhoneNumber").value("09123456789"))
                .andExpect(jsonPath("$.result.sourceComment").value("test source comment"))
                .andExpect(jsonPath("$.result.sourceAddressBase").value("test source base"))
                .andExpect(jsonPath("$.result.sourceAddressHouseNumber").value("1"))
                .andExpect(jsonPath("$.result.sourceAddressHomeUnit").value("1"))
                .andExpect(jsonPath("$.result.destinations[0].fullName").value("destination full name"))
                .andExpect(jsonPath("$.result.destinations[0].phoneNumber").value("09123456789"))
                .andExpect(jsonPath("$.result.destinations[0].comment").value("destination comment"))
                .andExpect(jsonPath("$.result.destinations[0].addressBase").value("destination address"))
                .andExpect(jsonPath("$.result.destinations[0].addressHouseNumber").value("1"))
                .andExpect(jsonPath("$.result.destinations[0].addressHomeUnit").value("1"))
                .andExpect(jsonPath("$.result.destinations[0].priceRange").value("UP_TO_ONE"))
                .andExpect(jsonPath("$.result.ownerId").value(ownerId))
                .andExpect(jsonPath("$.result.price").value(40000.0));
    }

    @Test
    @SneakyThrows
    void givenBoxOrderObject_whenCreateBoxOrder_thenReturnSavedBoxOrder() {
        // given

        List<DestinationNode> destinationNodes = new ArrayList<>();
        destinationNodes.add(DestinationNode.builder()
                .address(Address.builder()
                        .homeUnit("1")
                        .houseNumber("1")
                        .base("destination address")
                        .build())
                .priceRange(PriceRange.UP_TO_ONE)
                .comment("destination comment")
                .phoneNumber("09123456789")
                .fullName("destination full name")
                .x(2.0)
                .y(2.0)
                .build());

        BoxOrder boxOrder = BoxOrder.builder()
                .orderType(OrderType.BIKE)
                .source(SourceNode.builder()
                        .fullName("test source full name")
                        .phoneNumber("09123456789")
                        .comment("test source comment")
                        .address(Address.builder()
                                .base("test source base")
                                .houseNumber("1")
                                .homeUnit("1")
                                .build())
                        .x(1.0)
                        .y(1.0)
                        .build())
                .owner(Objects.requireNonNull(clientRepository.findById(1L).get()))
                .destinations(destinationNodes)
                .build();

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order")
                .build();

        String json = jsonWriter.writeValueAsString(orderMapper.boxOrderToBoxOrderDto(boxOrder));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("save.success", null, locale)))
                .andExpect(jsonPath("$.result.orderType").value("BIKE"))
                .andExpect(jsonPath("$.result.sourceFullName").value("test source full name"))
                .andExpect(jsonPath("$.result.sourcePhoneNumber").value("09123456789"))
                .andExpect(jsonPath("$.result.sourceComment").value("test source comment"))
                .andExpect(jsonPath("$.result.sourceAddressBase").value("test source base"))
                .andExpect(jsonPath("$.result.sourceAddressHouseNumber").value("1"))
                .andExpect(jsonPath("$.result.sourceAddressHomeUnit").value("1"))
                .andExpect(jsonPath("$.result.destinations[0].fullName").value("destination full name"))
                .andExpect(jsonPath("$.result.destinations[0].phoneNumber").value("09123456789"))
                .andExpect(jsonPath("$.result.destinations[0].comment").value("destination comment"))
                .andExpect(jsonPath("$.result.destinations[0].addressBase").value("destination address"))
                .andExpect(jsonPath("$.result.destinations[0].addressHouseNumber").value("1"))
                .andExpect(jsonPath("$.result.destinations[0].addressHomeUnit").value("1"))
                .andExpect(jsonPath("$.result.destinations[0].priceRange").value("UP_TO_ONE"))
                .andExpect(jsonPath("$.result.ownerId").value(ownerId))
                .andExpect(jsonPath("$.result.price").value(40000.0));
    }

    @Test
    @SneakyThrows
    void givenBoxOrderObject_whenCreateBoxOrder_returnUpdatedBoxOrder() {
        // given
        List<DestinationNode> destinationNodes = new ArrayList<>();
        destinationNodes.add(DestinationNode.builder()
                .address(Address.builder()
                        .homeUnit("1")
                        .houseNumber("1")
                        .base("destination address")
                        .build())
                .priceRange(PriceRange.UP_TO_ONE)
                .comment("destination comment")
                .phoneNumber("09123456789")
                .fullName("destination full name")
                .y(2.0)
                .x(2.0)
                .build());

        BoxOrder boxOrder = BoxOrder.builder()
                .orderType(OrderType.BIKE)
                .source(SourceNode.builder()
                        .fullName("test source full name")
                        .phoneNumber("09123456789")
                        .comment("test source comment")
                        .address(Address.builder()
                                .base("test source base")
                                .houseNumber("1")
                                .homeUnit("1")
                                .build())
                        .y(1.0)
                        .x(1.0)
                        .build())
                .owner(Objects.requireNonNull(clientRepository.findById(1L).get()))
                .destinations(destinationNodes)
                .price(40000.0)
                .build();

        boxOrder = boxOrderRepository.save(boxOrder);

        boxOrder.setOrderType(OrderType.CAR);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order")
                .build();

        String json = jsonWriter.writeValueAsString(orderMapper.boxOrderToBoxOrderDto(boxOrder));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("update.success", null, locale)))
                .andExpect(jsonPath("$.result.orderType").value("CAR"))
                .andExpect(jsonPath("$.result.sourceFullName").value("test source full name"))
                .andExpect(jsonPath("$.result.sourcePhoneNumber").value("09123456789"))
                .andExpect(jsonPath("$.result.sourceComment").value("test source comment"))
                .andExpect(jsonPath("$.result.sourceAddressBase").value("test source base"))
                .andExpect(jsonPath("$.result.sourceAddressHouseNumber").value("1"))
                .andExpect(jsonPath("$.result.sourceAddressHomeUnit").value("1"))
                .andExpect(jsonPath("$.result.destinations[0].fullName").value("destination full name"))
                .andExpect(jsonPath("$.result.destinations[0].phoneNumber").value("09123456789"))
                .andExpect(jsonPath("$.result.destinations[0].comment").value("destination comment"))
                .andExpect(jsonPath("$.result.destinations[0].addressBase").value("destination address"))
                .andExpect(jsonPath("$.result.destinations[0].addressHouseNumber").value("1"))
                .andExpect(jsonPath("$.result.destinations[0].addressHomeUnit").value("1"))
                .andExpect(jsonPath("$.result.destinations[0].priceRange").value("UP_TO_ONE"))
                .andExpect(jsonPath("$.result.ownerId").value(ownerId))
                .andExpect(jsonPath("$.result.price").value(40000.0));
    }

    @Test
    @SneakyThrows
    void givenBoxOrderId_whenCreateBoxOrder_thenReturnRemoveSuccess() {
        // given
        List<DestinationNode> destinationNodes = new ArrayList<>();
        destinationNodes.add(DestinationNode.builder()
                .address(Address.builder()
                        .homeUnit("1")
                        .houseNumber("1")
                        .base("destination address")
                        .build())
                .priceRange(PriceRange.UP_TO_ONE)
                .comment("destination comment")
                .phoneNumber("09123456789")
                .fullName("destination full name")
                .y(2.0)
                .x(2.0)
                .build());

        BoxOrder boxOrder = BoxOrder.builder()
                .orderType(OrderType.BIKE)
                .source(SourceNode.builder()
                        .fullName("test source full name")
                        .phoneNumber("09123456789")
                        .comment("test source comment")
                        .address(Address.builder()
                                .base("test source base")
                                .houseNumber("1")
                                .homeUnit("1")
                                .build())
                        .y(1.0)
                        .x(1.0)
                        .build())
                .owner(Objects.requireNonNull(clientRepository.findById(1L).get()))
                .destinations(destinationNodes)
                .price(40000.0)
                .build();

        long id = boxOrderRepository.save(boxOrder).getId();

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order/{id}")
                .uriVariables(Map.of("id", id))
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON);

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("remove.success", null, locale)))
                .andExpect(jsonPath("$.result").isEmpty());

        assertFalse(boxOrderRepository.existsById(1L));
    }

    @Test
    @SneakyThrows
    void givenBoxOrderSearchParameters_whenCreateBoxOrder_thenReturnBoxOrder() {
        // given
        List<DestinationNode> destinationNodes = new ArrayList<>();
        destinationNodes.add(DestinationNode.builder()
                .address(Address.builder()
                        .homeUnit("1")
                        .houseNumber("1")
                        .base("destination address")
                        .build())
                .priceRange(PriceRange.UP_TO_ONE)
                .comment("destination comment")
                .phoneNumber("09123456789")
                .fullName("destination full name")
                .x(2.0)
                .y(2.0)
                .build());

        BoxOrder boxOrder = BoxOrder.builder()
                .orderType(OrderType.BIKE)
                .source(SourceNode.builder()
                        .fullName("test source full name")
                        .phoneNumber("09123456789")
                        .comment("test source comment")
                        .address(Address.builder()
                                .base("test source base")
                                .houseNumber("1")
                                .homeUnit("1")
                                .build())
                        .x(1.0)
                        .y(1.0)
                        .build())
                .owner(Objects.requireNonNull(clientRepository.findById(1L).get()))
                .destinations(destinationNodes)
                .price(40000.0)
                .build();

        List<DestinationNode> destinationNodes1 = new ArrayList<>();
        destinationNodes1.add(DestinationNode.builder()
                .address(Address.builder()
                        .homeUnit("1")
                        .houseNumber("1")
                        .base("destination address")
                        .build())
                .priceRange(PriceRange.UP_TO_ONE)
                .comment("destination comment")
                .phoneNumber("09123456789")
                .fullName("destination full name")
                .x(2.0)
                .y(2.0)
                .build());

        BoxOrder boxOrder1 = BoxOrder.builder()
                .orderType(OrderType.CAR)
                .source(SourceNode.builder()
                        .fullName("test source full name")
                        .phoneNumber("09123456789")
                        .comment("test source comment")
                        .address(Address.builder()
                                .base("test source base")
                                .houseNumber("1")
                                .homeUnit("1")
                                .build())
                        .x(1.0)
                        .y(1.0)
                        .build())
                .owner(Objects.requireNonNull(clientRepository.findById(1L).get()))
                .price(40000.0)
                .destinations(destinationNodes1)
                .build();

        long id = boxOrderRepository.save(boxOrder).getId();
        boxOrderRepository.save(boxOrder1);

        BoxOrderSearchWrapper wrapper = BoxOrderSearchWrapper.builder()
                .ownerId(1L)
                .orderType(OrderType.BIKE).build();

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/order")
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uriComponents.toUriString())
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("ownerId", wrapper.getOwnerId().toString())
                .queryParam("orderType", wrapper.getOrderType().toString())
                .queryParam("page", "0");

        // when then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageSource.getMessage("search.success", null, locale)))
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result[0].id").value(id))
                .andExpect(jsonPath("$.result[0].orderType").value("BIKE"))
                .andExpect(jsonPath("$.result[0].sourceFullName").value("test source full name"))
                .andExpect(jsonPath("$.result[0].sourcePhoneNumber").value("09123456789"))
                .andExpect(jsonPath("$.result[0].sourceComment").value("test source comment"))
                .andExpect(jsonPath("$.result[0].sourceAddressBase").value("test source base"))
                .andExpect(jsonPath("$.result[0].sourceAddressHouseNumber").value("1"))
                .andExpect(jsonPath("$.result[0].sourceAddressHomeUnit").value("1"))
                .andExpect(jsonPath("$.result[0].destinations").isArray())
                .andExpect(jsonPath("$.result[0].destinations[0].fullName").value("destination full name"))
                .andExpect(jsonPath("$.result[0].destinations[0].phoneNumber").value("09123456789"))
                .andExpect(jsonPath("$.result[0].destinations[0].comment").value("destination comment"))
                .andExpect(jsonPath("$.result[0].destinations[0].priceRange").value("UP_TO_ONE"))
                .andExpect(jsonPath("$.result[0].destinations[0].addressBase").value("destination address"))
                .andExpect(jsonPath("$.result[0].destinations[0].addressHouseNumber").value("1"))
                .andExpect(jsonPath("$.result[0].destinations[0].addressHomeUnit").value("1"))
                .andExpect(jsonPath("$.result[0].ownerId").value(ownerId))
                .andExpect(jsonPath("$.result[0].price").value(40000.0));
    }
}
