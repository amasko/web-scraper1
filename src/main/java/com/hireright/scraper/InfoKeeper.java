package main.java.com.hireright.scraper;

import java.util.ArrayList;
import java.util.List;

public enum InfoKeeper {
    INSTANCE;
    private List<String> info = new ArrayList<String>();

    public void storeInfo(String message) {
        info.add(message);
    }

    public void printInfo() {
        System.out.println("******************************************");
        for (String message : info) {
            System.out.println(message);
        }
        System.out.println("******************************************");
    }

    public void printError(String error) {
        System.out.println("******************************************");
        System.out.println(error);
        System.out.println("******************************************");
    }

}
