package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.dto.BoxOrderDto;
import com.snapp.boxdemo.exception.DuplicateEntityException;
import com.snapp.boxdemo.exception.NotFoundException;
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
    public ResponseEntity<BoxOrderDto> getBoxOrder(@PathVariable Long orderId) {
        BoxOrderDto dto = service.getBoxOrder(orderId);
        if (dto == null)
            throw new NotFoundException(source.getMessage("error.notFound", null, Locale.getDefault()));

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> postBoxOrder(@RequestBody @Valid BoxOrderDto dto) {
        if (service.exist(dto.getId()))
            throw new DuplicateEntityException(source.getMessage("error.duplicate", null, Locale.getDefault()));

        service.saveOrUpdateBoxOrder(dto);
        return ResponseEntity.ok(source.getMessage("save.success", null, Locale.getDefault()));
    }

    @PutMapping
    public ResponseEntity<String> putBoxOrder(@RequestBody @Valid BoxOrderDto dto) {
        if (!service.exist(dto.getId()))
            throw new NotFoundException(source.getMessage("error.notFound", null, Locale.getDefault()));

        service.saveOrUpdateBoxOrder(dto);
        return ResponseEntity.ok(source.getMessage("update.success", null, Locale.getDefault()));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteBoxOrder(@PathVariable Long orderId) {
        if (!service.exist(orderId))
            throw new NotFoundException(source.getMessage("error.notFound", null, Locale.getDefault()));

        service.removeBoxOrder(orderId);
        return ResponseEntity.ok(source.getMessage("remove.success", null, Locale.getDefault()));
    }
}
