package com.example.vegetables.service.impl;

import com.example.vegetables.model.Person;
import com.example.vegetables.dao.PersonMapper;
import com.example.vegetables.service.IPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 欠款信息 服务实现类
 * </p>
 *
 * @author 朱归华
 * @since 2022-04-28
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

}
