package com.carllhw.example.netty.enums;

/**
 * Option name
 *
 * @author carllhw
 */
public enum OptionName {

    /**
     * EXAMPLE
     */
    EXAMPLE("example");

    private final String code;

    OptionName(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
