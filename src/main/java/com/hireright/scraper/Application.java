package main.java.com.hireright.scraper;

public class Application {

    public static void main(String[] args) {
        PageProcessor pp = new PageProcessor();
//        pp.processPage("http://www.buzzfeed.com");
        pp.processPage("http://www.theguardian.com/");
        System.out.println("done");
    }
}
