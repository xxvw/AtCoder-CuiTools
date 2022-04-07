package com.github.xrozl.atcoder.tools;

public enum LanguageSet {

    JAVA(true, "javac", "java", false, null, "\u001b[00;33mJava\u001b[00m"),
    GPLUSPLUS(true, "g++", "./a.out", false, null, "\u001b[00;33mC++\u001b[00m"),

    DART(false, null, "dart", false, null, "\u001b[00;33mDart\u001b[00m"),
    PERL(false, null, "perl", false, null, "\u001b[00;33mPerl\u001b[00m"),
    RUBY(false, null, "ruby", false, null, "\u001b[00;33mRuby\u001b[00m"),
    PYTHON(false, null, "python3", false, null, "\u001b[00;33mPython\u001b[00m");

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
        } else if (file.endsWith(".pl")) {
            return PERL;
        } else if (file.endsWith(".rb")) {
            return RUBY;
        } else if (file.endsWith(".cpp")) {
            return GPLUSPLUS;
        } else if (file.endsWith(".dart")) {
            return DART;
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
