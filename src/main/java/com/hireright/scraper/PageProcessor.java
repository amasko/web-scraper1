package main.java.com.hireright.scraper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alex on 06.08.2014.
 */
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
            URL url = new URL(urlStr);
            InputStreamReader inpReader = new InputStreamReader(url.openStream());
            System.out.println(inpReader.getEncoding());
            in = new BufferedReader(inpReader); //, "UTF-8"
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out, false)));//, "UTF-8"
            String str;
            while ((str = in.readLine()) != null) {
                //result += str;
                writer.write(str);
            }
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
