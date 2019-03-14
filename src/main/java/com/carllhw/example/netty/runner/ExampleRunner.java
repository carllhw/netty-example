package com.carllhw.example.netty.runner;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.carllhw.example.netty.Example;
import com.carllhw.example.netty.ExampleHelp;
import com.carllhw.example.netty.discard.DiscardServer;
import com.carllhw.example.netty.enums.ExampleType;
import com.carllhw.example.netty.enums.OptionName;

/**
 * Example runner
 *
 * @author carllhw
 */
@Component
public class ExampleRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Example example = new ExampleHelp();
        if (!args.containsOption(OptionName.EXAMPLE.getCode())) {
            example.run(args);
            return;
        }
        List<String> optionValues = args.getOptionValues(OptionName.EXAMPLE.getCode());
        if (optionValues.isEmpty()) {
            example.run(args);
            return;
        }
        ExampleType exampleType = ExampleType.codeOf(optionValues.get(0));
        if (exampleType == null) {
            example.run(args);
            return;
        }
        switch (exampleType) {
            case DISCARD_SERVER:
                example = new DiscardServer();
                break;
            case DISCARD_CLIENT:
                break;
            default:
                break;
        }
        example.run(args);
    }
}
