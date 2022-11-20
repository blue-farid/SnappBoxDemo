package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.dto.BoxOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class BoxOrderController {

    @GetMapping("/{orderId}")
    public ResponseEntity<BoxOrderDto> getBoxOrder(@PathVariable Long orderId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<String> postBoxOrder(@RequestBody BoxOrderDto dto) {
        return null;
    }

    @PutMapping
    public ResponseEntity<String> putBoxOrder(@RequestBody BoxOrderDto dto) {
        return null;
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteBoxOrder(@PathVariable Long orderId) {
        return null;
    }
}
