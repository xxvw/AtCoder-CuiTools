package com.github.xrozl.atcoder.tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Solver {

    private String input, correct;
    private Process p;
    private String clazz;
    private long t;
    private LanguageSet lang;

    public Solver(String input, String correct, String clazz, double timeLimit, LanguageSet lang) {
        this.input = input;
        this.correct = correct;
        this.clazz = clazz;
        this.t = (long) (timeLimit * 1000);
        this.lang = lang;
    }

    public ResultSet run() {
        ProcessBuilder builder = null;
        if (lang.getCmd4Run().split(" ").length > 1) {
            List<String> cmd = new ArrayList<>();
            for (String str : lang.getCmd4Run().split(" ")) {
                cmd.add(str.replaceAll("%name_without%", clazz.split("\\.")[0]));
            }
            cmd.add(clazz);
            builder = new ProcessBuilder(cmd);
        } else {
            builder = new ProcessBuilder(lang.getCmd4Run().replaceAll("%name_without%", clazz.split("\\.")[0]), clazz);
        }
        try {
            p = builder.start();
            long start = System.currentTimeMillis();
            //System.out.println("Process Start");
            PrintWriter out = new PrintWriter(p.getOutputStream());
            out.print(input);
            out.print(System.getProperty("line.separator"));
            out.flush();
            //System.out.println("Input Sent");
            StringBuffer buffer = new StringBuffer();

            new Thread(() -> {
                try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String line;
                    //System.out.println("Get Output");
                    while ((line = input.readLine()) != null) {
                        //System.out.println(line);
                        buffer.append(line);
                    }
                } catch (IOException e) {
                    //
                }
            }).start();

            if (!p.waitFor(t, TimeUnit.MILLISECONDS)) {
                p.destroy();
                long end = System.currentTimeMillis();
                return new ResultSet(end-start, ErrorCode.TLE);
            }
            long end = System.currentTimeMillis();

            if (p.exitValue() != 0) {
                return new ResultSet(end-start, ErrorCode.WA);
            }

            List<Byte> bufferBytes = new ArrayList<>();
            List<Byte> correctBytes = new ArrayList<>();
            for (byte b : buffer.toString().getBytes(StandardCharsets.UTF_8)) {
                //System.out.printf("%d ", b);
                if (b == 0x0d) continue;
                bufferBytes.add(b);
            }
            //System.out.println();
            //System.out.println("== correct ==");
            for (byte b : correct.getBytes(StandardCharsets.UTF_8)) {
                //System.out.printf("%d ", b);
                if (b == 0x0d) continue;
                correctBytes.add(b);
            }
            // check both are same
            if (bufferBytes.size() != correctBytes.size()) {
                return new ResultSet(end-start, ErrorCode.WA);
            }
            for (int i = 0; i < bufferBytes.size(); i++) {
                if (bufferBytes.get(i) != correctBytes.get(i)) {
                    p.destroy();
                    return new ResultSet(end-start, ErrorCode.WA);
                }
            }
            p.destroy();
            return new ResultSet(end-start, ErrorCode.AC);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            p.destroy();
            return new ResultSet(0, ErrorCode.WA);
        }
        p.destroy();
        return new ResultSet(0, ErrorCode.CE);
    }
}