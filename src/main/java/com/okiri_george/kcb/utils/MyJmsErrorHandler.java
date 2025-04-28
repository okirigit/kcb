package com.okiri_george.kcb.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

@Slf4j
public class MyJmsErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        log.info("Error processing JMS message: {}", t.getMessage());
    }
}
