package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.entity.OrderType;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;
import com.snapp.boxdemo.service.BoxOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{orderId}")
    public ResponseEntity<BaseResponseDto<BoxOrderDto>> getBoxOrder(@PathVariable Long orderId,
                                                                    @RequestHeader(value = "Accept-Language",
                                                                            required = false) Locale locale) {
        BoxOrderDto dto = service.getBoxOrder(orderId);
        return ResponseEntity.ok().body(BaseResponseDto.<BoxOrderDto>builder().result(dto).message(
                source.getMessage("get.success", null, locale)
        ).build());
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto<BoxOrderDto>> postBoxOrder(@Valid @RequestBody BoxOrderDto dto,
                                                                     @RequestHeader(value = "Accept-Language",
                                                                             required = false) Locale locale) {
        return ResponseEntity.ok().body(BaseResponseDto.<BoxOrderDto>builder().result(service.saveBoxOrder(dto)).message(
                source.getMessage("save.success", null, locale)
        ).build());
    }

    @PutMapping
    public ResponseEntity<BaseResponseDto<BoxOrderDto>> putBoxOrder(@RequestBody @Valid BoxOrderDto dto,
                                                                    @RequestHeader(value = "Accept-Language",
                                                                            required = false) Locale locale) {
        return ResponseEntity.ok().body(BaseResponseDto.<BoxOrderDto>builder().result(service.updateBoxOrder(dto)).message(
                source.getMessage("update.success", null, locale)
        ).build());
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<BaseResponseDto<Object>> deleteBoxOrder(@PathVariable Long orderId,
                                                                  @RequestHeader(value = "Accept-Language",
                                                                          required = false) Locale locale) {
        service.removeBoxOrder(orderId);
        return ResponseEntity.ok().body(BaseResponseDto.builder().message(
                source.getMessage("remove.success", null, locale)
        ).build());
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto<Object>> searchBoxOrder(
            @RequestParam String ownerFullName,
            @RequestParam String ownerId,
            @RequestParam String ownerPhoneNumber,
            @RequestParam OrderType orderType,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date creationDate,
            @RequestParam int page,
            @RequestHeader("Accept-Language") Locale locale
    ) {
        List<BoxOrderDto> orders = service.searchBoxOrders(BoxOrderSearchWrapper.builder()
                .ownerId(ownerId).ownerFullName(ownerFullName).ownerPhoneNumber(ownerPhoneNumber)
                .orderType(orderType).creationDate(creationDate).build(), page);

        return ResponseEntity.ok().body(BaseResponseDto.builder().message(
                source.getMessage("search.success", null, locale)
        ).result(orders).build());
    }
}
