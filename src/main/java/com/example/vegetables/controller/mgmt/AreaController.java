package com.example.vegetables.controller.mgmt;


import com.example.vegetables.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 朱归华
 * @since 2022-10-10
 */
@RestController
@RequestMapping("/area")
public class AreaController {


    @Autowired
    private IAreaService areaService;



}
