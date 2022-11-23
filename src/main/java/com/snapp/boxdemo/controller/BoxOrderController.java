package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.service.BoxOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class BoxOrderController {

    private final BoxOrderService service;
    private final MessageSource source;

    @GetMapping("/{orderId}")
    public ResponseEntity<BaseResponseDto<BoxOrderDto>> getBoxOrder(@PathVariable Long orderId) {
        BoxOrderDto dto = service.getBoxOrder(orderId);
        return ResponseEntity.ok().body(BaseResponseDto.<BoxOrderDto>builder().result(dto).message(
                source.getMessage("get.success", null, Locale.getDefault())
        ).build());
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto<BoxOrderDto>> postBoxOrder(@RequestBody @Valid BoxOrderDto dto) {
        return ResponseEntity.ok().body(BaseResponseDto.<BoxOrderDto>builder().result(service.saveBoxOrder(dto)).message(
                source.getMessage("save.success", null, Locale.getDefault())
        ).build());
    }

    @PutMapping
    public ResponseEntity<BaseResponseDto<BoxOrderDto>> putBoxOrder(@RequestBody @Valid BoxOrderDto dto) {
        return ResponseEntity.ok().body(BaseResponseDto.<BoxOrderDto>builder().result(service.updateBoxOrder(dto)).message(
                source.getMessage("update.success", null, Locale.getDefault())
        ).build());
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<BaseResponseDto<Object>> deleteBoxOrder(@PathVariable Long orderId) {
        service.removeBoxOrder(orderId);
        return ResponseEntity.ok().body(BaseResponseDto.builder().message(
                source.getMessage("remove.success", null, Locale.getDefault())
        ).build());
    }
}
