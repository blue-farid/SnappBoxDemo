package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.dto.BoxOrderDto;
import com.snapp.boxdemo.exception.DuplicateEntityException;
import com.snapp.boxdemo.exception.NotFoundException;
import com.snapp.boxdemo.service.BoxOrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@PropertySource("classpath:/api_messages.properties")
public class BoxOrderController {

    private final BoxOrderService service;
    private final String saveSuccess;
    private final String updateSuccess;
    private final String removeSuccess;
    private final String notFound;
    private final String duplicate;

    public BoxOrderController(BoxOrderService service, @Value("${save.success}") String saveSuccess,
                              @Value("${update.success}") String updateSuccess,
                              @Value("${remove.success}") String removeSuccess,
                              @Value("${error.notFound}") String notFound,
                              @Value("${error.duplicate}") String duplicate) {
        this.service = service;
        this.saveSuccess = saveSuccess;
        this.updateSuccess = updateSuccess;
        this.removeSuccess = removeSuccess;
        this.notFound = notFound;
        this.duplicate = duplicate;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<BoxOrderDto> getBoxOrder(@PathVariable Long orderId) {
        BoxOrderDto dto = service.getBoxOrder(orderId);
        if (dto == null)
            throw new NotFoundException(notFound);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> postBoxOrder(@RequestBody BoxOrderDto dto) {
        if (service.exist(dto.getId()))
            throw new DuplicateEntityException(duplicate);

        service.saveOrUpdateBoxOrder(dto);
        return ResponseEntity.ok(saveSuccess);
    }

    @PutMapping
    public ResponseEntity<String> putBoxOrder(@RequestBody BoxOrderDto dto) {
        if (!service.exist(dto.getId()))
            throw new NotFoundException(notFound);

        service.saveOrUpdateBoxOrder(dto);
        return ResponseEntity.ok(updateSuccess);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteBoxOrder(@PathVariable Long orderId) {
        if (!service.exist(orderId))
            throw new NotFoundException(notFound);

        service.removeBoxOrder(orderId);
        return ResponseEntity.ok(removeSuccess);
    }
}
