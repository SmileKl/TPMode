package com.example.vegetables.service.impl;

import com.example.vegetables.service.ITestInterface;

public class TestInterfaceImpl implements ITestInterface {
    @Override
    public String checkName(String name) {
        return "大帅哥";
    }
}
