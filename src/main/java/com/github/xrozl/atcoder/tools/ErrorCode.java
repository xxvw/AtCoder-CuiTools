package com.github.xrozl.atcoder.tools;

public enum ErrorCode {
    AC("\u001b[00;32m"), TLE("\u001b[00;33m"), CE("\u001b[00;36m"), WA("\u001b[00;31m"), NONE("\u001b[00;34m");

    private String color;
    ErrorCode(String color) {
        this.color = color;
    }

    public static ErrorCode from(int code) {
        switch (code) {
            case 0:
                return AC;
            case 1:
                return TLE;
            case 2:
                return CE;
            case 3:
                return WA;
            default:
                return NONE;
        }
    }

    public void out(long time) {
        System.out.println(color + this.name() + " \u001b[37m(" + time + "ms)\u001b[00m");
    }
}
