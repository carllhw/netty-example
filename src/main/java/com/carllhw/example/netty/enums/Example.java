package com.carllhw.example.netty.enums;

/**
 * Example
 *
 * @author carllhw
 */
public enum Example {

    /**
     * DISCARD_SERVER
     */
    DISCARD_SERVER("discard-server");

    private final String code;

    Example(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean isEqual(String code) {
        return this.code.equals(code);
    }

}
