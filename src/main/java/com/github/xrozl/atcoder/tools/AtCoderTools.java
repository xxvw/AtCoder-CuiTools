package com.github.xrozl.atcoder.tools;

import jdk.jfr.FlightRecorder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AtCoderTools {

    public static void main(String[] args) throws IOException, InterruptedException {
        args = new String[]{"abc243", "a", "Main.java"};
        if (args.length < 3) {
            System.out.println("Usage: atc <contest> <level> <program_file>");
            System.exit(1);
        } else {
            String contest = args[0];
            String level = args[1];
            String contestName = contest.replaceAll(contest.substring(3).toLowerCase(Locale.ROOT), "");
            LanguageSet lang = LanguageSet.getLang(args[2]);
            //System.out.println(contestName);
            // abc arc agc check args[0] startwith
            if (contestName.equals("arc") || contestName.equals("agc") || contestName.equals("abc")) {
                List<ContestSample> samples = new ArrayList<>();
                String contestNumber = contest.substring(3);

                Connection connection = Jsoup.connect(UrlBuilder(contestNumber, contestName, contest, level));
                connection.timeout(10000);
                connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
                connection.referrer("https://www.google.com");
                connection.ignoreContentType(true);
                connection.method(Connection.Method.GET);
                Connection.Response resp = connection.execute();
                String html = resp.body();
                String pre = "入力例";
                String suf = "出力例";
                List<String> in = new ArrayList<>();
                List<String> out = new ArrayList<>();
                double time = Double.parseDouble(html.split("Time Limit: ")[1].split(" ")[0]);
                String[] lines3 = html.split("</h3><pre>");
                List<String> a = new ArrayList<>();
                for (String s : lines3) {
                    if (s.contains("</pre>") && !s.contains("<!DOCTYPE html>")) {
                        a.add(s.split("</pre>")[0]);
                    }
                }
                int half = a.size() / 2;
                for (int i = 0; i < half; i++) {
                    in.add(a.get(i));
                    i++;
                    out.add(a.get(i));
                }
                long m = (long) (time * 1000);
                System.out.println("\u001b[00;40m===== " + contestName.toUpperCase() + contestNumber + " " + level.toUpperCase() + " =====\u001b[00m\nTime Limit: " + time + "s (" + m + "ms)\nLanguage: " + lang.get4Mat());
                for (int i = 0; i < in.size(); i++) {
                    samples.add(new ContestSample(in.get(i), out.get(i)));
                }
                if (lang.requireCompile()) {
                    CompileTester tester = new CompileTester(args[2], lang);
                    tester.compile();
                    boolean b = tester.getResult();
                    System.out.println("Compile " + (b ? "\u001b[00;32mSuccess\u001b[00m" : "\u001b[00;31mFailed\u001b[00m"));
                    if (!b) System.exit(1);
                }
                for (int i = 0; i < samples.size(); i++) {
                    Solver solver = new Solver(samples.get(i).in, replaceLast(samples.get(i).out, System.getProperty("line.separator"), ""), args[2], time, lang);
                    System.out.printf("\u001b[00;35mSample\u001b[00m " + (i + 1) + "\u001b[00m : ");
                    ResultSet result = solver.run();
                    result.getErrorCode().out(result.getTime());
                }
                System.exit(0);
            } else {
                System.out.println("Contest name must start with abc, arc or agc");
                System.exit(1);
            }
        }
    }

    static String UrlBuilder(String a, String b, String c, String d) {
        return "https://atcoder.jp/contests/" + b + a + "/tasks/" + b + a + "_" + d;
    }

    static String replaceLast(String baseString, String regex, String replacement) {
        return baseString.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
    }

}
