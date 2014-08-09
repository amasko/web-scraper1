package main.java.com.hireright.scraper;

import java.util.regex.Pattern;

public class CommandExecutor {
    public void execute(String[] args) {
        if (args.length == 0 || args.length > 6)
            InfoKeeper.INSTANCE.printError("Incorrect input: check out number of arguments!");
        String urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        String filePathPattern = "([a-zA-Z]:)?((\\\\|/)[a-zA-Z0-9_\\.\\s-]+)+(\\\\|/)?|([a-zA-Z0-9_\\.-]+)";
        String addr = args[0];
        //scraping only one web page
        if (Pattern.matches(urlPattern, addr)) {
            SinglePageExecutor pageExecutor = new SinglePageExecutor(args);
            pageExecutor.run();
        } else if (Pattern.matches(filePathPattern, addr)) {
            System.out.println("path to file: " + addr);
        } else {
            System.out.println("Error");


        }
    }
}
