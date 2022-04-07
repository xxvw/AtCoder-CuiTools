package com.github.xrozl.atcoder.tools;

import java.io.File;

public class CompileTester {

    private File f;
    private Process p;
    private StringBuilder sb;

    public CompileTester(File f) {
        this.f = f;
    }

    public CompileTester(String path) {
        this(new File(path));
    }

    public void compile() {
        try {
            p = Runtime.getRuntime().exec("javac " + f.getPath());
            sb = new StringBuilder();
            new Thread(() -> {
                try {
                    while (true) {
                        String line = new String(p.getInputStream().readAllBytes());
                        sb.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    while (true) {
                        String line = new String(p.getErrorStream().readAllBytes());
                        sb.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOutput() {
        return sb.toString();
    }

    public boolean getResult() {
        for (String line : sb.toString().split("\n")) {
            if (line.contains("error")) {
                return false;
            }
        }
        return true;
    }
}
