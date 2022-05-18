package com.example.vegetables.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 公式计算器
 **/
@Slf4j
public class EquationCalculator {


    /**
     * @param formula 公式
     * @param varListWithValue 变量和值的key-value
     * @return 返回结果Float
     */
    public static Float calculator(String formula, Map<String, Float> varListWithValue) {
        formula = formula.replaceAll(" ", "");
        String equationLeft = formula.split("=")[0];
        /** 代回公式进行替换 **/
        String newFormula = replaceEquation(formula,varListWithValue);
        /** 进行计算 **/
        float result = calc(newFormula);
        log.info("最终结果:" + equationLeft + "=" + result);
        return result;
    }

    /**
     * 进行计算
     **/
    private static float calc(String newFormula) {
        boolean stillHaveCalcSymbol = false;
        do {
            /** 寻找最后一个左括号里面到第一个右括号里面1的内容 **/
            char[] formulaArray = newFormula.toCharArray();
            for (int i = 0; i < formulaArray.length; i++) {
                if (formulaArray[i] == '+' || formulaArray[i] == '-'
                        || formulaArray[i] == '*' || formulaArray[i] == '/'
                        || formulaArray[i] == '(' || formulaArray[i] == ')') {
                    stillHaveCalcSymbol = true;
                } else {
                    stillHaveCalcSymbol = false;
                }
            }
            if (stillHaveCalcSymbol) {
                String resultFormula = "";
                //找最内层的括号里面的内容出来（含括号）
                for (int i = 0; i < formulaArray.length; i++) {
                    if (formulaArray[i] == ')') {
                        int begin = 0;
                        for (int j = i; j >= 0; j--) {
                            if (formulaArray[j] == '(') {
                                begin = j;
                                break;
                            }
                        }
                        String calcString = newFormula.substring(begin, i + 1);
                        resultFormula = newFormula.replace(calcString, calcProc(calcString) + "");
                        break;
                    }
                }
                newFormula = resultFormula;
            }
        } while (stillHaveCalcSymbol);
        //最后得到普通的顺序无括号公式：
       log.info(newFormula);
        //最后一次计算:
        return calcProc("(" + newFormula.split("=")[1] + ")");
    }

    /**
     * 详细计算过程
     **/
    private static float calcProc(String calcString) {
        String[] calcSymbol = {"\\*", "\\/", "\\+", "\\-"};
        char[] calcSymbolChar = {'*', '/', '+', '-'};
        boolean haveSymbol = true;
        float result = 0f;
        while (haveSymbol) {
           log.info("calcStr:" + calcString);
            char[] calcCharArr = calcString.toCharArray();
            result = 0f;
            for (int i = 0; i < calcSymbol.length; i++) {
                boolean alreadyFind = false;
                for (int j = 0; j < calcCharArr.length; j++) {
                    if (calcCharArr[j] == calcSymbolChar[i]) {
                        //System.out.println("找到了" + calcSymbolChar[i]);
                        //以符号为中心，以左右两边的其他符号为边界找到两边的数
                        float num1;
                        float num2;
                        int bottom = 0;
                        for (int k = j - 1; k >= 0 && (calcCharArr[k] >= '0' && calcCharArr[k] <= '9' || calcCharArr[k] == '.'); k--) {
                            bottom = k;
                        }
                        num1 = Float.parseFloat(calcString.substring(bottom, j));
                       log.info("num1:" + num1);
                        int top = 0;
                        for (int k = j + 1; k < calcString.length() && (calcCharArr[k] >= '0' && calcCharArr[k] <= '9' || calcCharArr[k] == '.'); k++) {
                            top = k;
                        }
                        num2 = Float.parseFloat(calcString.substring(j + 1, top + 1));
                       log.info("num2:" + num2);
                        switch (calcSymbolChar[i]) {
                            case '*':
                                result = num1 * num2;
                                break;
                            case '/':
                                result = num1 / num2;
                                break;
                            case '+':
                                result = num1 + num2;
                                break;
                            case '-':
                                result = num1 - num2;
                                break;
                        }
                        calcString = calcString.replace(calcString.substring(bottom, top + 1), String.format("%.5f", result));
                        alreadyFind = true;
                        break;
                    }
                }
                if (alreadyFind) break;
            }
            haveSymbol = calcString.contains("*") || calcString.contains("/") || calcString.contains("+") || calcString.contains("-");
        }
        return result;
    }

    /**
     * 代回公式进行替换
     **/
    private static String replaceEquation(String formula, Map<String, Float> varListWithValue) {
        String newFormula = formula;
        for (String key : varListWithValue.keySet()) {
            newFormula = newFormula.replaceAll(key, varListWithValue.get(key) + "");
        }
       log.info(newFormula);
        return newFormula;
    }
}
