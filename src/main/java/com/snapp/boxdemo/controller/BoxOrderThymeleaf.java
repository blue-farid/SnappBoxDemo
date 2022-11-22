package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.dto.BoxOrderDto;
import com.snapp.boxdemo.service.BoxOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class BoxOrderThymeleaf {
    private final BoxOrderService service;

    @GetMapping
    public String updateForm(Model model) {
        Iterable<BoxOrderDto> orders = service.getAll();
        model.addAttribute("orders", orders);
        return "index";
    }
}
