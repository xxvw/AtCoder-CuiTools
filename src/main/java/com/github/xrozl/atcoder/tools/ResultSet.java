package com.github.xrozl.atcoder.tools;

public class ResultSet {

    private long time;
    private ErrorCode errorCode;

    public ResultSet(long time, ErrorCode errorCode) {
        this.time = time;
        this.errorCode = errorCode;
    }

    public long getTime() {
        return time;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}