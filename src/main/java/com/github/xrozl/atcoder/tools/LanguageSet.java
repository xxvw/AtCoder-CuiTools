package com.github.xrozl.atcoder.tools;

public enum LanguageSet {

    JAVA(true, "javac", "java", false, null, "\u001b[00;33mJava\u001b[00m"),
    PYTHON(false, null, "python3", false, null, "\u001b[00;34mPython\u001b[00m");

    private boolean compile, errorCheck;
    private String cmd4Compile, cmd4Run, L4Mat, errorChk;

    LanguageSet(boolean requireCompile, String cmd4Compile, String cmd4Run, boolean errorCheck, String errorChk, String L4Mat) {
        this.compile = requireCompile;
        this.cmd4Compile = cmd4Compile;
        this.cmd4Run = cmd4Run;
        this.L4Mat = L4Mat;
        this.errorCheck = errorCheck;
        this.errorChk = errorChk;
    }

    public static LanguageSet getLang(String file) {
        if (file.endsWith(".java")) {
            return JAVA;
        } else if (file.endsWith(".py")) {
            return PYTHON;
        }
        return null;
    }

    public boolean requireCompile() {
        return compile;
    }

    public String getCmd4Compile() {
        return cmd4Compile;
    }

    public String getCmd4Run() {
        return cmd4Run;
    }

    public String get4Mat() {
        return L4Mat;
    }

    public boolean requireErrorCheck() {
        return errorCheck;
    }

    public String getErrorChk() {
        return errorChk;
    }
}
