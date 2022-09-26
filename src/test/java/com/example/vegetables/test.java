package com.example.vegetables;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


public class test {
    public static void main(String[] args) throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> varListWithValue = new DefaultContext<>();
        varListWithValue.put("unitPrice", 0.01);
        varListWithValue.put("meterDegree", 127.98);
        Object execute = runner.execute("total=unitPrice*meterDegree", varListWithValue, null, true, false);
        System.out.println(execute);
    }

}
