package main.java.com.hireright.scraper;

import java.util.ArrayList;
import java.util.List;

public class SinglePageExecutor implements Runnable {

    private String addr;
    private String[] wordArray;
    private boolean v = false, w = false, c = false, e = false;

    public SinglePageExecutor(String addr, String[] wodrArray, boolean v, boolean w, boolean c, boolean e) {
        this.addr = addr;
        this.wordArray = wodrArray;
        this.v = v;
        this.w = w;
        this.c = c;
        this.e = e;
    }

    @Override
    public void run() {
        List<String> data = new ArrayList<String>();
        PageProcessor pp = new PageProcessor();
        //Raw page content
        String pageContent = pp.processPage(addr);
        long t0 = System.currentTimeMillis();
        DataProcessor dp = new DataProcessor(pageContent);
        data.add("\nFor web page " + addr + ":\n");
        if (c) {
            data.add("Number of characters on the page: " + dp.charactersNumber());
            data.add("******************************************\n");
        }
        if (w) {
            for (String word : wordArray) {
                Integer number = dp.wordsNumber(word.toLowerCase());
                if (number != null)
                    data.add("word \"" + word + "\" is found " + number + " times;\n");
                 else
                    data.add("word \"" + word + "\" is not found on the page;\n");
            }
            data.add("******************************************\n");
        }
        if (e) {
            //temp
//            dp.loadToFile();
            dp.extractAllSentences();
            for (String word : wordArray) {
                if (dp.getFreq().get(word.toLowerCase()) != null) {
                    data.add("sentences for \"" + word + "\":");
                    for (String sentence : dp.extractSentence(word.toLowerCase())) {
                        data.add(sentence);
                    }
                    data.add("         ***\n");
                }
            }
            data.add("******************************************");
        }
        if (v) {
            long t1 = System.currentTimeMillis();
            long processTime = t1 - t0;
            data.add("Scraping time: " + pp.getScrapTime() + " ms.; \n" +
                    "Data processing time: " + processTime + " ms.;\n");
            data.add("******************************************");
        }
        data.add("******************************************");
        InfoKeeper.INSTANCE.storeAll(data);
    }
}
