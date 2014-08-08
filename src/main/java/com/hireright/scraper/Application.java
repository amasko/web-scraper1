package main.java.com.hireright.scraper;

public class Application {

    public static void main(String[] args) {
        PageProcessor pp = new PageProcessor();
//        pp.processPage("http://www.buzzfeed.com");
//        pp.processPage("http://www.theguardian.com/");
//        pp.processPage("http://www.cnn.com/");
        pp.processPage("http://www.reddit.com/r/worldnews/comments/2cxuz4/obama_authorizes_targeted_airstrikes_in_iraq/");
        System.out.println("done");
    }
}
