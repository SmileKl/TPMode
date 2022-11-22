package com.example.vegetables.service.impl;

import com.example.vegetables.model.Category;
import com.example.vegetables.dao.CategoryMapper;
import com.example.vegetables.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 朱归华
 * @since 2022-11-22
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
