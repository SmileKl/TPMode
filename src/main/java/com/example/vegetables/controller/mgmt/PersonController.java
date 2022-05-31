package com.example.vegetables.controller.mgmt;


import com.example.vegetables.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 欠款信息 前端控制器
 * </p>
 *
 * @author 朱归华
 * @since 2022-04-28
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/person")
public class PersonController {

    private final RedisUtil redisUtil;

}
