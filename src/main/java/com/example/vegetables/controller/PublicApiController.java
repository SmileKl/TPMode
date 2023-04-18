package com.example.vegetables.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vegetables.listener.EasyExcelReadEventListener;
import com.example.vegetables.model.Area;
import com.example.vegetables.model.Category;
import com.example.vegetables.result.CodeMsgVO;
import com.example.vegetables.result.ScenesDimensionNew;
import com.example.vegetables.result.SpecialNewDataInput;
import com.example.vegetables.result.SpecialOldDataInput;
import com.example.vegetables.service.IAreaService;
import com.example.vegetables.service.ICategoryService;
import groovy.util.logging.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class PublicApiController {


    @PostMapping("/dp2Buy")
    public List<CodeMsgVO> dp2Buy(int num) {
        List<CodeMsgVO> list = new ArrayList<>();
        CodeMsgVO codeMsgVO1 = new CodeMsgVO();
        codeMsgVO1.setCode("90%");
        codeMsgVO1.setMsg(String.valueOf(num * 0.9));
        list.add(codeMsgVO1);

        CodeMsgVO codeMsgVO2 = new CodeMsgVO();
        codeMsgVO2.setCode("80%");
        codeMsgVO2.setMsg(String.valueOf(num * 0.8));
        list.add(codeMsgVO2);

        CodeMsgVO codeMsgVO3 = new CodeMsgVO();
        codeMsgVO3.setCode("70%");
        codeMsgVO3.setMsg(String.valueOf(num * 0.7));
        list.add(codeMsgVO3);

        CodeMsgVO codeMsgVO4 = new CodeMsgVO();
        codeMsgVO4.setCode("60%");
        codeMsgVO4.setMsg(String.valueOf(num * 0.6));
        list.add(codeMsgVO4);

        return list;
    }

    @PostMapping("/dp2Safe")
    public List<CodeMsgVO> dp2Safe(int num) {
        List<CodeMsgVO> list = new ArrayList<>();
        CodeMsgVO codeMsgVO1 = new CodeMsgVO();
        codeMsgVO1.setCode("110%");
        codeMsgVO1.setMsg(String.valueOf(num * 1.1));
        list.add(codeMsgVO1);

        CodeMsgVO codeMsgVO2 = new CodeMsgVO();
        codeMsgVO2.setCode("120%");
        codeMsgVO2.setMsg(String.valueOf(num * 1.2));
        list.add(codeMsgVO2);

        CodeMsgVO codeMsgVO3 = new CodeMsgVO();
        codeMsgVO3.setCode("130%");
        codeMsgVO3.setMsg(String.valueOf(num * 1.3));
        list.add(codeMsgVO3);

        CodeMsgVO codeMsgVO4 = new CodeMsgVO();
        codeMsgVO4.setCode("140%");
        codeMsgVO4.setMsg(String.valueOf(num * 1.4));
        list.add(codeMsgVO4);

        CodeMsgVO codeMsgVO5 = new CodeMsgVO();
        codeMsgVO5.setCode("150%");
        codeMsgVO5.setMsg(String.valueOf(num * 1.5));
        list.add(codeMsgVO5);

        CodeMsgVO codeMsgVO6 = new CodeMsgVO();
        codeMsgVO6.setCode("160%");
        codeMsgVO6.setMsg(String.valueOf(num * 1.6));
        list.add(codeMsgVO6);

        CodeMsgVO codeMsgVO7 = new CodeMsgVO();
        codeMsgVO7.setCode("170%");
        codeMsgVO7.setMsg(String.valueOf(num * 1.7));
        list.add(codeMsgVO7);

        return list;
    }

    @PostMapping("/inputData")
    public void inputData(MultipartFile multipartFile, Integer number, HttpServletResponse response) throws IOException {
        List<Object> objectList = new ArrayList<>();
        inputData(multipartFile, objectList, SpecialNewDataInput.class);
        log.info("objectList==={}", JSON.toJSONString(objectList));
//        InputStream inputStream;
//        List<SpecialOldDataInput> list = new ArrayList<>();
//        List<Object> objectList = new ArrayList<>();
////        try {
//            inputStream = multipartFile.getInputStream();
//            EasyExcel.read(inputStream, SpecialOldDataInput.class, new EasyExcelReadEventListener() {
//                @Override
//                public void invoke(Object data, AnalysisContext context) {
////                    SpecialOldDataInput SpecialOldDataInput = (SpecialOldDataInput) data;
////                    DefaultXlsxReadContext xlsReadContext = (DefaultXlsxReadContext) context;
//                    objectList.add(data);
//                }
//
//                @Override
//                public void onException(Exception e, AnalysisContext analysisContext) {
//                    log.error("解析失败，但是继续解析下一行:{}", e.getMessage());
//                    // 如果是某一个单元格的转换异常 能获取到具体行号
//                    // 如果要获取头的信息 配合invokeHeadMap使用
//                    if (e instanceof ExcelDataConvertException) {
//                        ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) e;
//                        log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
//                                excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData().getStringValue());
//                        String msg = "第" + (excelDataConvertException.getRowIndex()) + "行，第" + (excelDataConvertException.getColumnIndex()) + "列解析异常，数据为:"
//                                + excelDataConvertException.getCellData().getStringValue();
//                        log.info(msg);
//                    }
//                }
//            }).sheet().headRowNumber(5).doRead();
//
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        log.info("list->[{}]", JSON.toJSONString(list));
//        log.info("list->[{}]", JSON.toJSONString(objectList));

//        InputStream inputStream;
//        List<SpecialNewDataInput> list = new ArrayList<>();
////        try {
//        inputStream = multipartFile.getInputStream();
//        EasyExcel.read(inputStream, SpecialNewDataInput.class, new EasyExcelReadEventListener() {
//            @Override
//            public void invoke(Object data, AnalysisContext context) {
//                SpecialNewDataInput SpecialNewDataInput = (SpecialNewDataInput) data;
//                List<String> troubleList = Arrays.stream(SpecialNewDataInput.getTroubleStr().split(",")).collect(Collectors.toList());
//                SpecialNewDataInput.setTroubleStr(JSON.toJSONString(troubleList));
////                    DefaultXlsxReadContext xlsReadContext = (DefaultXlsxReadContext) context;
//                list.add(SpecialNewDataInput);
//            }
//
//            @Override
//            public void onException(Exception e, AnalysisContext analysisContext) {
//                log.error("解析失败，但是继续解析下一行:{}", e.getMessage());
//                // 如果是某一个单元格的转换异常 能获取到具体行号
//                // 如果要获取头的信息 配合invokeHeadMap使用
//                if (e instanceof ExcelDataConvertException) {
//                    ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) e;
//                    log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
//                            excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData().getStringValue());
//                    String msg = "第" + (excelDataConvertException.getRowIndex()) + "行，第" + (excelDataConvertException.getColumnIndex()) + "列解析异常，数据为:"
//                            + excelDataConvertException.getCellData().getStringValue();
//                    log.info(msg);
//                }
//            }
//        }).sheet().headRowNumber(5).doRead();

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        log.info("list->[{}]", JSON.toJSONString(list));


    }

    private void inputData(MultipartFile multipartFile, List<Object> objectList, Class clazz) {
        List<String> msgList = new ArrayList<>();
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
            EasyExcel.read(inputStream, clazz, new EasyExcelReadEventListener() {
                @Override
                public void invoke(Object data, AnalysisContext context) {
//                    SpecialOldDataInput SpecialOldDataInput = (SpecialOldDataInput) data;
//                    DefaultXlsxReadContext xlsReadContext = (DefaultXlsxReadContext) context;
                    objectList.add(data);
                }

                @Override
                public void onException(Exception e, AnalysisContext analysisContext) {
                    log.error("解析失败，但是继续解析下一行:{}", e.getMessage());
                    // 如果是某一个单元格的转换异常 能获取到具体行号
                    // 如果要获取头的信息 配合invokeHeadMap使用
                    if (e instanceof ExcelDataConvertException) {
                        ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) e;
                        log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                                excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData().getStringValue());
                        String msg = "第" + (excelDataConvertException.getRowIndex()) + "行，第" + (excelDataConvertException.getColumnIndex()) + "列解析异常，数据为:"
                                + excelDataConvertException.getCellData().getStringValue();
                        msgList.add(msg);
                        log.info(msg);
                    }
                }
            }).sheet().headRowNumber(5).doRead();
        } catch (Exception e) {
            log.info("导入异常->>>{} \n", e.getMessage());
            log.info("详细保存信息->{}", JSON.toJSONString(e));
        }

    }

    @PostMapping("/stringParam")
    public void stringParam(@RequestBody String fCode){
        System.out.println(fCode);
    }

    public static void main(String[] args) throws IllegalAccessException {
//        BigDecimal decimal = BigDecimal.valueOf(4 * 0.334 + 4 * 0.333 + 4 * 0.333 + 1 * 0.334 + 1 * 0.333 + 1 * 0.333);
//        System.out.println(decimal);
//        System.out.println(NumberUtil.div(decimal, 6, 7));

//        BigDecimal decimal = BigDecimal.valueOf(4 * 0.1 + 4 * 0.1 + 4 * 0.1 + 1 * 0.1 + 1 * 0.1 + 1 * 0.1);
//        System.out.println(decimal);
//        System.out.println(NumberUtil.div(decimal, 6, 7));

//        String str = "{\"buildingScenes\":1.33,\"educateScenes\":2.50,\"goAloneScenes\":1.00,\"governScenes\":0.33,\"healthyScenes\":1.51,\"internetScenes\":2.50,\"lowCarbonScenes\":1.00,\"neighborScenes\":0.26,\"serveScenes\":1.00,\"subjectiveEvaluation\":0.83,\"trafficScenes\":2.18}";
//        String empty = "{}";
//        ScenesDimensionNew e = JSON.parseObject(str, ScenesDimensionNew.class);
////        System.out.println(scenesDimensionNew);
////        System.out.println(scenesDimensionNew.getEducateScenes());
//        Field[] fields = e.getClass().getDeclaredFields();
////        System.out.println(Arrays.toString(fields));
//        for (Field f : fields) {
//            f.setAccessible(true);
//            System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(e));
//        }

//        System.out.println(BigDecimal.ZERO.equals(new BigDecimal("0.00")));

//        List<String> list = CollUtil.newArrayList("aaa","bbb");
//        for (String s : list) {
//            System.out.println(s);
//        }
//        log.info("返回的项目id集合：{}", list);
        String str = "社区活动空间丰富,居住环境好,商业配套完善,生活便利舒适,幼托服务好,学习课程多,卫生健康好,居家养老服务完善,物业管理好,智慧平台方便,惠民服务收费合理,社区较安全、服务好,邻里关系融洽,邻里活动众多,社区积分很有用,邻里组织、党建工作较好,居民能方便参与社区管理,社区管理反馈好，处理率高,喜欢愿意推荐社区";
        List<String> troubleList = Arrays.stream(str.split(",")).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(troubleList));


    }
}
