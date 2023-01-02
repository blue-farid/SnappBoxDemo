package com.snapp.boxdemo.controller.api;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.entity.OrderType;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;
import com.snapp.boxdemo.security.util.SecurityUtils;
import com.snapp.boxdemo.service.BoxOrderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class BoxOrderController {

    private final BoxOrderService service;
    private final MessageSource source;

    private final SecurityUtils securityUtils;

    @ApiOperation(value = "get order by id")
    @ApiParam(name = "orderId", value = "order id", required = true)
    @ApiResponse(code = 200, message = "success",
            response = BaseResponseDto.class, examples = @Example(value =
            {@ExampleProperty(mediaType = "application/json", value = """
                    {
                      "message": "string",
                      "result": {
                        "destinations": [
                          {
                            "addressBase": "string",
                            "addressHomeUnit": "string",
                            "addressHouseNumber": "string",
                            "comment": "string",
                            "fullName": "string",
                            "id": 1,
                            "phoneNumber": "string",
                            "priceRange": "UP_TO_FIVE",
                            "x": 2.0,
                            "y": 2.0
                          }
                        ],
                        "id": 1,
                        "orderType": "BIKE",
                        "ownerId": 1,
                        "price": 40000,
                        "sourceAddressBase": "string",
                        "sourceAddressHomeUnit": "string",
                        "sourceAddressHouseNumber": "string",
                        "sourceComment": "string",
                        "sourceFullName": "string",
                        "sourcePhoneNumber": "string",
                        "sourceX": 1.0,
                        "sourceY": 1.0
                      }
                    }""")}))
    @GetMapping("/{orderId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<BaseResponseDto<BoxOrderDto>> getBoxOrder(@PathVariable Long orderId, Locale locale) {
        BoxOrderDto dto = service.getBoxOrder(orderId);
        securityUtils.checkOwner(dto.getOwnerId(), locale);
        return ResponseEntity.ok().body(BaseResponseDto.<BoxOrderDto>builder().result(dto).message(
                source.getMessage("get.success", null, locale)
        ).build());
    }

    @ApiOperation(value = "create new order")
    @ApiResponse(code = 200, message = "success",
            response = BaseResponseDto.class, examples = @Example(value =
            {@ExampleProperty(mediaType = "application/json", value = """
                    {
                      "message": "string",
                      "result": {
                        "destinations": [
                          {
                            "addressBase": "string",
                            "addressHomeUnit": "string",
                            "addressHouseNumber": "string",
                            "comment": "string",
                            "fullName": "string",
                            "id": 1,
                            "phoneNumber": "string",
                            "priceRange": "UP_TO_FIVE",
                            "x": 2.0,
                            "y": 2.0
                          }
                        ],
                        "id": 1,
                        "orderType": "BIKE",
                        "ownerId": 1,
                        "price": 40000,
                        "sourceAddressBase": "string",
                        "sourceAddressHomeUnit": "string",
                        "sourceAddressHouseNumber": "string",
                        "sourceComment": "string",
                        "sourceFullName": "string",
                        "sourcePhoneNumber": "string",
                        "sourceX": 1.0,
                        "sourceY": 1.0
                      }
                    }""")}))
    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<BaseResponseDto<BoxOrderDto>> postBoxOrder(@Valid @RequestBody BoxOrderDto dto, Locale locale) {
        securityUtils.checkOwner(dto.getOwnerId(), locale);
        return ResponseEntity.ok().body(BaseResponseDto.<BoxOrderDto>builder().result(service.saveBoxOrder(dto)).message(
                source.getMessage("save.success", null, locale)
        ).build());
    }

    @ApiOperation(value = "update the order")
    @ApiResponse(code = 200, message = "success",
            response = BaseResponseDto.class, examples = @Example(value =
            {@ExampleProperty(mediaType = "application/json", value = """
                    {
                      "message": "string",
                      "result": {
                        "destinations": [
                          {
                            "addressBase": "string",
                            "addressHomeUnit": "string",
                            "addressHouseNumber": "string",
                            "comment": "string",
                            "fullName": "string",
                            "id": 1,
                            "phoneNumber": "string",
                            "priceRange": "UP_TO_FIVE",
                            "x": 2.0,
                            "y": 2.0
                          }
                        ],
                        "id": 1,
                        "orderType": "BIKE",
                        "ownerId": 1,
                        "price": 40000,
                        "sourceAddressBase": "string",
                        "sourceAddressHomeUnit": "string",
                        "sourceAddressHouseNumber": "string",
                        "sourceComment": "string",
                        "sourceFullName": "string",
                        "sourcePhoneNumber": "string",
                        "sourceX": 1.0,
                        "sourceY": 1.0
                      }
                    }""")}))
    @PutMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<BaseResponseDto<BoxOrderDto>> putBoxOrder(@RequestBody @Valid BoxOrderDto dto, Locale locale) {
        securityUtils.checkOwner(dto.getOwnerId(), locale);
        return ResponseEntity.ok().body(BaseResponseDto.<BoxOrderDto>builder().result(service.updateBoxOrder(dto)).message(
                source.getMessage("update.success", null, locale)
        ).build());
    }

    @ApiOperation(value = "delete the order by id")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<BaseResponseDto<Object>> deleteBoxOrder(@PathVariable Long orderId, Locale locale) {
        service.removeBoxOrder(orderId);
        return ResponseEntity.ok().body(BaseResponseDto.builder().message(
                source.getMessage("remove.success", null, locale)
        ).build());
    }

    @ApiOperation(value = "search orders")
    @ApiResponse(code = 200, message = "success",
            response = BaseResponseDto.class, examples = @Example(value =
            {@ExampleProperty(mediaType = "application/json", value = """
                    {
                      "message": "string",
                      "result": {
                        "destinations": [
                          {
                            "addressBase": "string",
                            "addressHomeUnit": "string",
                            "addressHouseNumber": "string",
                            "comment": "string",
                            "fullName": "string",
                            "id": 1,
                            "phoneNumber": "string",
                            "priceRange": "UP_TO_FIVE",
                            "x": 2.0,
                            "y": 2.0
                          }
                        ],
                        "id": 1,
                        "orderType": "BIKE",
                        "ownerId": 1,
                        "price": 40000,
                        "sourceAddressBase": "string",
                        "sourceAddressHomeUnit": "string",
                        "sourceAddressHouseNumber": "string",
                        "sourceComment": "string",
                        "sourceFullName": "string",
                        "sourcePhoneNumber": "string",
                        "sourceX": 1.0,
                        "sourceY": 1.0
                      }
                    }""")}))
    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<BaseResponseDto<Object>> searchBoxOrder(
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) OrderType orderType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date creationDate,
            @RequestParam int page, Locale locale) {
        securityUtils.checkOwner(ownerId, locale);
        List<BoxOrderDto> orders = service.searchBoxOrders(BoxOrderSearchWrapper.builder()
                .ownerId(ownerId).orderType(orderType).creationDate(creationDate).build(), page);

        return ResponseEntity.ok().body(BaseResponseDto.builder().message(
                source.getMessage("search.success", null, locale)
        ).result(orders).build());
    }
}
