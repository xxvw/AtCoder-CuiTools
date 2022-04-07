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

    public static void main(String[] args) throws IOException {

        args = new String[3];
        args[0] = "abc246";
        args[1] = "d";
        args[2] = "Main.java";
        /*if (args.length < 3) {
            System.out.println("Usage: atc <contest> <level> <java_file>");
            System.exit(1);
        } else {*/
        String contest = args[0];
        String level = args[1];
        String contestName = contest.replaceAll(contest.substring(3).toLowerCase(Locale.ROOT), "");
        System.out.println(contestName);
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
            for (String str : html.split("<h3>")) {
                if (str.contains(pre)) {
                    str = str.split("</pre>")[0].split("</h3>")[1].replaceAll("<pre>", "");
                    //System.out.println(str);
                    in.add(str);
                } else if (str.contains(suf)) {
                    str = str.split("</pre>")[0].split("</h3>")[1].replaceAll("<pre>", "");
                    //System.out.println(str);
                    out.add(str);
                }
            }
            for (int i = 0; i < in.size(); i++) {
                samples.add(new ContestSample(in.get(i), out.get(i)));
                System.out.println("ID: " + i);
                System.out.println("IN: " + in.get(i).replaceAll("\n", ""));
                System.out.println("OUT: " + out.get(i).replaceAll("\n", ""));
            }
        } else {
            System.out.println("Contest name must start with abc, arc or agc");
            System.exit(1);
        }
    }

    static String UrlBuilder(String a, String b, String c, String d) {
        return "https://atcoder.jp/contests/" + b + a + "/tasks/" + b + a + "_" + d;
    }
}
