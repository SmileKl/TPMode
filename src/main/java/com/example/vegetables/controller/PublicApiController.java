package com.example.vegetables.controller;

import cn.hutool.core.util.NumberUtil;
import com.example.vegetables.result.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/publicApi")
public class PublicApiController {


    /**
     * 测试网络
     * @return
     */
    @GetMapping("/testApi")
    public ResultVO<Object> testApi(){
        return ResultVO.wrapSuccessResponse("成功");
    }

    /**
     * 筹码集中度
     * @param num
     * @return
     */
    @GetMapping("/phoneCM")
    public BigDecimal phoneChouMa(@RequestParam("num") Float num) {
        return NumberUtil.round(1 / (1 - num / 100), 2);
    }



}
