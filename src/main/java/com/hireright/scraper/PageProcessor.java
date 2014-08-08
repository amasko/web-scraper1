package main.java.com.hireright.scraper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PageProcessor {
    private long scrapTime;

    public PageProcessor() {
    }

    public String processPage(String urlStr) {
        BufferedReader in;
        String result = "";
        StringBuilder sb = new StringBuilder();
        try {
            /*URLConnection con = url.openConnection();
            InputStream is =con.getInputStream();*/
            URL url = new URL(urlStr);
            InputStreamReader inpReader = new InputStreamReader(url.openStream());
            in = new BufferedReader(inpReader);
            String str;
            long t0 = System.nanoTime();
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
            result = sb.toString();
            long t1 = System.nanoTime();
            scrapTime = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public long getScrapTime() {
        return scrapTime;
    }
}
