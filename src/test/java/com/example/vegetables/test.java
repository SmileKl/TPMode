package com.example.vegetables;

import com.alibaba.fastjson.JSON;
import com.example.vegetables.dao.AreaMapper;
import com.example.vegetables.model.Area;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class test {

    @Resource
    private AreaMapper areaMapper;

    public static void main(String[] args) throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> varListWithValue = new DefaultContext<>();
        varListWithValue.put("unitPrice", 0.01);
        varListWithValue.put("meterDegree", 127.98);
        Object execute = runner.execute("total=unitPrice*meterDegree", varListWithValue, null, true, false);
        System.out.println(execute);
    }

    @Test
    public void test01() {
//        List<Area> areas = areaMapper.queryList(1, 10);
//        List<String> list = null;
//        System.out.println(list.size());
        String str = "{\"Mon,Wen\":[{\"start\":\"08:00\",\"end\":\"20:00\"}]}";
        Object parse = JSON.parseObject(str,String.class);
        System.out.println(parse);
    }

}
