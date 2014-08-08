package main.java.com.hireright.scraper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PageProcessor {
    private File out, out1;

    public PageProcessor() {
        out = new File("output.txt");
        out1 = new File("outputtemp.txt");
        if (!out.exists()) {
            try {
                boolean c = out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void processPage(String urlStr) {
        BufferedReader in = null;
        BufferedWriter writer = null;
        BufferedWriter writer1 = null;
        String result = "";
        StringBuilder sb = new StringBuilder();
        try {
            /*URLConnection con = url.openConnection();
            InputStream is =con.getInputStream();*/
            URL url = new URL(urlStr);
            InputStreamReader inpReader = new InputStreamReader(url.openStream());
            //System.out.println(inpReader.getEncoding());
            in = new BufferedReader(inpReader); //, "UTF-8"
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out, false)));//, "UTF-8"
            writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out1, false)));//, "UTF-8"
            String str;
            long t00 = System.nanoTime();
            while ((str = in.readLine()) != null) {
                sb.append(str);
                //replacing markup from page and writing it to the output
            }
            result = sb.toString()
                    .replaceAll("(<script).*?(</script>)|(<style).*?(/style>)|<.*?>|&\\S*?;", "");

            long t11 = System.nanoTime();
            long s1 = TimeUnit.NANOSECONDS.toMillis(t11 - t00);
            System.out.println("time scraping: " + s1);

            // (<script).*?(/script>) - regex for script block
            // (<style).*?(/style>) - remove CSS
            // &\S*?; - special HTML characters
            // "&end;; adding in case of multiple white space occurrence

            long t0 = System.nanoTime();
            String result1 = result.replaceAll("\\s{2,}|\\t|\\.\\s", "&end;; ");
            writer.write(result1);
            long t1 = System.nanoTime();
            long s = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
            System.out.println("time removing: " + s);

            //temporary

            DataProcessor dp = new DataProcessor(result1);
            System.out.println("chars count: " + dp.charactersNumber(result));
//            Map<String, Integer> stringIntegerHashMap = dp.wordsNumber(result);
//            for (String st : stringIntegerHashMap.keySet()) {
//                writer1.write(st + "--> " + stringIntegerHashMap.get(st));
//                writer1.newLine();
//            }
            Integer tempp = dp.wordsNumber("job".toLowerCase());
            if (tempp != null) {
                System.out.println("words job: " + tempp);
            } else {
                System.out.println("there's no such a word");
            }
            if (tempp != null) {
                dp.extractSentence("Job".toLowerCase());
            } else {
                System.out.println("there's no such a word");

            }
            in.close();
            writer.close();
            writer1.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return result;

    }

}
