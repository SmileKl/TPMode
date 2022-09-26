package com.example.vegetables.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;
import org.springframework.stereotype.Component;

@Slf4j
public class LogStdOutImpl implements Log {

    public LogStdOutImpl(String clazz) {
        // Do Nothing
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s);
        e.printStackTrace(System.err);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.info(s);
    }

    @Override
    public void trace(String s) {
        log.info(s);
    }

    @Override
    public void warn(String s) {
        log.info(s);
    }
}
