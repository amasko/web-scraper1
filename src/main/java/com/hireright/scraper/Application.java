package main.java.com.hireright.scraper;

public class Application {

    public static void main(String[] args) {
        PageProcessor pp = new PageProcessor();
        pp.processPage("http://www.cnn.com");
        System.out.println("done");
    }
}