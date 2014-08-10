package main.java.com.hireright.scraper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public enum InfoKeeper {
    INSTANCE;
    private final List<String> info = new ArrayList<String>();

    public void storeInfo(String message) {
        info.add(message);
    }

    public void storeAll(List<String> data) {
        info.addAll(data);
    }

    public void printInfo() {
        File out = new File("output-log.txt");
        if (out.exists()) {
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out, false)));//, "UTF-8"
            for (String message : info) {
                System.out.println(message);
                writer.write(message);
                writer.newLine();
            }
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void printError(String error) {
        System.out.println("******************************************");
        System.out.println(error);
        System.out.println("******************************************");
    }

    public List<String> getInfo() {
        return info;
    }

}
