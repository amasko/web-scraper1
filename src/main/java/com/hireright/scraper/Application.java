package main.java.com.hireright.scraper;

import java.io.Console;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    public static void main(String[] args) {
       // PageProcessor pp = new PageProcessor();
//        pp.processPage("http://www.buzzfeed.com");
//        pp.processPage("http://www.theguardian.com/");
        //pp.processPage("http://www.cnn.com/");
        //pp.processPage("http://www.reddit.com/r/worldnews/comments/2cxuz4/obama_authorizes_targeted_airstrikes_in_iraq/");
//        System.out.println("done");
        CommandExecutor ce = new CommandExecutor();
        ce.execute(args);

    }
}
