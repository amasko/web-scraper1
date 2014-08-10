package main.java.com.hireright.scraper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class CommandExecutor {
    private boolean v = false , w = false, c = false, e = false;
    private boolean flagsCorrect = true;
    public void execute(String[] args) {
        if (args.length == 0 || args.length > 6)
            InfoKeeper.INSTANCE.printError("Incorrect input: check out number of arguments!");
        else {
            String urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            String filePathPattern = "([a-zA-Z]:)?((\\\\|/)[a-zA-Z0-9_\\.-]+)+(\\\\|/)?|([a-zA-Z0-9_\\.-]+)";
            String addr = args[0];
            String words = args[1];
            String[] wordArray = words.split(",");
            for (int i = 2; i < args.length; i++) {
                setFlags(args[i]);
            }
            //scraping only one web page
            if (Pattern.matches(urlPattern, addr) && flagsCorrect) {
                SinglePageExecutor pageExecutor = new SinglePageExecutor(addr, wordArray, v, w, c, e);
                pageExecutor.run();
            } else if (Pattern.matches(filePathPattern, addr) && flagsCorrect) {
                //starting a threads for every single page to process
                String[] urls = getUrls(addr);
                if (urls == null) {
                    InfoKeeper.INSTANCE.printError("File is empty or doesn't exist.");
                } else {
                    ExecutorService es = Executors.newCachedThreadPool();
                    ExecutorCompletionService<Object> ec = new ExecutorCompletionService<Object>(es);
                    for (String url : urls) {
                        ec.submit(new SinglePageExecutor(url, wordArray, v, w, c, e), new Object());
                    }
                    //after returning from that loop all tasks are guaranteed to be finished
                    for (String url : urls) {
                        try {
                            ec.take();
                        } catch (InterruptedException e1) {
                            InfoKeeper.INSTANCE.printError("Problems occured while executing task for current web page," +
                                    " process interrupted.");
                        }
                    }
                    es.shutdown();
                }
            } else {
                System.out.println("Error");
                InfoKeeper.INSTANCE.printError("There might be a problem with input data.\n" +
                        "Try to follow next input pattern:\n" +
                        "http://www.cnn.com/ word1,word2,word3 -v -c -e -w\n" +
                        "Or specify path to a file with a list of URLs as follows:\n" +
                        "C:/some-directory/input.txt word1,word2,word3 -v -c -e -w\n" +
                        "You also can just put file with URLs in the directory with .jar file and write:\n" +
                        "input.txt word1,word2,word3 -v -c -e");
            }
            InfoKeeper.INSTANCE.printInfo();
        }
    }

    private String[] getUrls(String path) {
        String UTF_BOM = "\uFEFF";
        String[] urls = null;
        File input = new File(path);
        if (!input.exists()) {
            InfoKeeper.INSTANCE.printError("Wrong path to file!");
        } else {
            try {
                String text = new Scanner(input, "UTF8").useDelimiter("\\A").next();
                //check for UTF-8 Byte Order Mark from the first line
                if (text.startsWith(UTF_BOM))
                    text = text.substring(1);
                //addresses could be separated with spaces or written in column
                urls = text.split("\\s+");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        return urls;
    }

    private void setFlags(String flag) {
        if (flag.equals("-v")) {
            v = true;

        } else if (flag.equals("-w")) {
            w = true;

        } else if (flag.equals("-c")) {
            c = true;

        } else if (flag.equals("-e")) {
            e = true;

        } else {
            flagsCorrect = false;
            InfoKeeper.INSTANCE.printError("Error: incompatible input, check your flags");
        }
    }
}
