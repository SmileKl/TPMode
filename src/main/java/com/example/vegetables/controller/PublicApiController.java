package com.example.vegetables.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vegetables.model.Area;
import com.example.vegetables.model.Category;
import com.example.vegetables.service.IAreaService;
import com.example.vegetables.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PublicApiController {

    @Autowired
    private IAreaService areaService;
    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/shardingApi")
    public void shardingApi(){
        List<Area> list = areaService.list();
        areaService.saveBatch(list);

        List<Category> categoryList = categoryService.list();
        categoryService.saveBatch(categoryList);
    }

    @PostMapping("/insertOne")
    public void insertOne(){
        Area area = new Area();
        area.setProjectId(6699L);
        areaService.save(area);
    }

    @PostMapping("/queryOne")
    public void queryOne(){
        LambdaQueryWrapper<Area> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Area::getProjectId,10086L);
        List<Area> list = areaService.list(lqw);
        List<Area> areaList = areaService.list();
        System.out.println(list);
        System.out.println(areaList);
    }
}
