package com.example.vegetables.controller;

import com.example.vegetables.result.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publicApi")
public class PublicApiController {

    @GetMapping("/testApi")
    public RestResult<Object> testApi(){
        return RestResult.wrapSuccessResponse("成功");
    }


}
