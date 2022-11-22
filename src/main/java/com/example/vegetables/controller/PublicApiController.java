package com.example.vegetables.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.URLUtil;
import com.example.vegetables.result.ResultVO;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

@RestController
@RequestMapping("/publicApi")
public class PublicApiController {


    /**
     * 测试网络
     *
     * @return
     */
    @GetMapping("/testApi")
    public ResultVO<Object> testApi() {
        return ResultVO.wrapSuccessResponse("成功");
    }

    /**
     * 筹码集中度
     *
     * @param num
     * @return
     */
    @GetMapping("/phoneCM")
    public BigDecimal phoneChouMa(@RequestParam("num") Float num) {
        return NumberUtil.round(1 / (1 - num / 100), 2);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        String str = "https://wlsq.jst.zj.gov.cn/sqgzc/vote-h5/#/pages/vote/index/index?surveyNumber=1582574747162832896&appKey=AK09291296236800";
//        String encode = URLEncoder.encode(str,"UTF-8");
//        System.out.println(encode);
//        System.out.println(URLUtil.decode(encode));
//        System.out.println(NumberUtil.div(122, 0));

        BigDecimal big = new BigDecimal("-100");
        System.out.println(big);
        System.out.println(big.abs());



    }


    //今天赚了多少钱

    //赊账的钱

    //精确到客户(按姓名分类记账、保存手机号码、微信二维码)

}
