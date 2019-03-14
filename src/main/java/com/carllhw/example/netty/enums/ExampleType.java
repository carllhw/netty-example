package com.carllhw.example.netty.enums;

/**
 * Example
 *
 * @author carllhw
 */
public enum ExampleType {

    /**
     * DISCARD_CLIENT
     */
    DISCARD_CLIENT("discard-client"),

    /**
     * DISCARD_SERVER
     */
    DISCARD_SERVER("discard-server");

    private final String code;

    ExampleType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean isEqual(String code) {
        return this.code.equals(code);
    }

    /**
     * query based on code
     *
     * @param code code
     * @return Example
     */
    public static ExampleType codeOf(String code) {
        ExampleType[] exampleTypes = values();
        for (ExampleType exampleType : exampleTypes) {
            if (exampleType.code.equals(code)) {
                return exampleType;
            }
        }
        return null;
    }
}
