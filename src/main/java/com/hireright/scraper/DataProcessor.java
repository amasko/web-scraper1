package main.java.com.hireright.scraper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 07.08.2014.
 */
public class DataProcessor {

    private String string;

    public DataProcessor(String string) {
        this.string = string;
    }

    public int charactersNumber(String str) {
        //not sure if white space counts
        char[] array = string.replaceAll("\\s", "").toCharArray();
        return array.length;
    }

    public Integer wordsNumber(String str) {
        String[] s = string.split("\\W+");
        Map<String, Integer> freq = new HashMap<String, Integer>();
        for (String word : s) {
            Integer count = freq.get(word.toLowerCase());
            if (count == null) freq.put(word.toLowerCase(), 1);
            else freq.put(word.toLowerCase(), count + 1);
        }
        return freq.get(str);
    }
}
