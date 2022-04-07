package com.github.xrozl.atcoder.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CompileTester {

    private File f;
    private Process p;
    private StringBuilder sb;
    private LanguageSet lang;

    public CompileTester(String f, LanguageSet lang) {
        this.f = new File(f);
        this.lang = lang;
    }

    public void compile() {
        try {
            ProcessBuilder builder = null;
            if (lang.getCmd4Compile().split(" ").length > 1) {
                List<String> cmd = new ArrayList<>();
                for (String str : lang.getCmd4Compile().split(" ")) {
                    cmd.add(str);
                }
                cmd.add(f.getName());
                builder = new ProcessBuilder(cmd);
            } else {
                builder = new ProcessBuilder(lang.getCmd4Compile(), f.getName());
            }
            p = builder.start();
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
