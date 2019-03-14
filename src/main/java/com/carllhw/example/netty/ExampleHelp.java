package com.carllhw.example.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

import com.carllhw.example.netty.enums.ExampleType;

/**
 * Example help
 *
 * @author carllhw
 */
@Slf4j
public class ExampleHelp implements Example {

    @Override
    public void run(ApplicationArguments args) {
        log.info("Available examples:");
        ExampleType[] exampleTypes = ExampleType.values();
        for (ExampleType exampleType : exampleTypes) {
            log.info(exampleType.getCode());
        }
    }
}
