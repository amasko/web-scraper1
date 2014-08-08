package main.java.com.hireright.scraper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandExecutor {

    private boolean v = false, w = false, c = false, e = false;
    public void execute(String[] args) {
        if (args.length == 0 || args.length > 6)
            printMessage("Error", "Incorrect input: check out number of arguments!");
        String addr = args[0];
//        Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(s);
        String currentDirTxt = "\\S*\\.txt$";
        String words = args[1];
        String[] wordArray = words.split(",");
        for (int i = 2; i < args.length; i++) {
            setFlags(args[i]);
        }

        PageProcessor pp = new PageProcessor();
        //Raw page content
        String pageContent = pp.processPage(addr);
        long t0 = System.currentTimeMillis();
        DataProcessor dp = new DataProcessor(pageContent);
        if (c)
            printMessage("Number of characters on the page", String.valueOf(dp.charactersNumber()));
        if (w) {
            for (String word : wordArray) {
                Integer number = dp.wordsNumber(word.toLowerCase());
                if (number != null)
                    printMessage("word \"" + word + "\" is found ", number + " times");
                 else
                    printMessage("word \"" + word + "\" is not found on the page", "");
            }
        }
        if (e) {
            dp.loadToFile();
            dp.extractAllSentences();
            for (String word : wordArray) {
                if (dp.getFreq().get(word.toLowerCase()) != null) {
                    for (String sentence : dp.extractSentence(word.toLowerCase())) {
                        System.out.println(sentence);
                    }
                    System.out.println("         ***");
                }
            }
            System.out.println("===================================================");
        }

        if (v) {
            long t1 = System.currentTimeMillis();
            long processTime = t1 - t0;
            System.out.println("Scraping time: " + pp.getScrapTime() + " ms.");
            System.out.println("Data processing time: " + processTime + " ms.");
            System.out.println("===================================================");
        }
    }

    public void printMessage(String title, String msg) {
        System.out.println(title +": "+msg);
//        System.out.println(msg);
        System.out.println("\n\r*******************************************************\n\r");

    }

    public void setFlags(String flag) {
        if (flag.equals("-v")) {
            v = true;

        } else if (flag.equals("-w")) {
            w = true;

        } else if (flag.equals("-c")) {
            c = true;

        } else if (flag.equals("-e")) {
            e = true;

        } else {
            printMessage("Error: ", "incompatible input; check your flags ");
        }
    }
}
