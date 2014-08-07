package main.java.com.hireright.scraper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PageProcessor {
    private File out;

    public PageProcessor() {
        out = new File("output.txt");
        if (!out.exists()) {
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void processPage(String urlStr) {
        BufferedReader in = null;
        BufferedWriter writer = null;
        String result = "";
        try {
            /*URLConnection con = url.openConnection();
            InputStream is =con.getInputStream();*/
            URL url = new URL(urlStr);
            InputStreamReader inpReader = new InputStreamReader(url.openStream());
            //System.out.println(inpReader.getEncoding());
            in = new BufferedReader(inpReader); //, "UTF-8"
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out, false)));//, "UTF-8"
            String str;
            long t00 = System.nanoTime();
            while ((str = in.readLine()) != null) {
                result += str;
                //replacing markup from page and writing it to the output
            }
            long t11 = System.nanoTime();
            long s1 = TimeUnit.NANOSECONDS.toMillis(t11 - t00);
            System.out.println("time scraping: " + s1);


            // "\u00a0","" - non-breaking space
            //
            // (<script).*?(/script>) - regex for script block
            // (<style).*?(/style>) - remove CSS
            // &\S*?; - special HTML characters
            // (<script).*?(/script>)|(<style).*?(/style>)

            long t0 = System.nanoTime();
            writer.write(result.replaceAll("(<script).*?(/script>)|(<style).*?(/style>)|<.*?>|&\\S*?;", "").replaceAll("\\s{2,}","&end;; "));//"\\<.*?\\>"
            long t1 = System.nanoTime();
            long s = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
            System.out.println("time removing: " + s);
            //result.replace
            in.close();
            writer.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return result;

    }

}
