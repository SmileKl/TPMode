package com.example.vegetables.controller;

import cn.hutool.core.util.NumberUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PhoneController {

    @GetMapping("/phoneCM/{num}")
    public BigDecimal phoneChouMa(@PathVariable("num") Float num) {
        return NumberUtil.round(1 / (1 - num / 100), 2);
    }
}
