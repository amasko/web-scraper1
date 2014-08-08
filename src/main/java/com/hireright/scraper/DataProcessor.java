package main.java.com.hireright.scraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataProcessor {
    private String string;

    public DataProcessor(String string) {
        this.string = string;
    }

    public int charactersNumber(String str) {
        //not sure if white space should count
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

    public void extractSentence(String str) {
        List<String> sentences = new ArrayList<String>(); //collection will contain all sentences in the text
        int fromIndex = 0;
        int toIndex = 0;
        String delim = "&end;;";
        while (toIndex >= 0) {                                //fromIndex < string.length() - 6 &&
            fromIndex = string.indexOf(delim, fromIndex);
            fromIndex += 6;
            toIndex = string.indexOf(delim, fromIndex);
            if (toIndex >= 0)
                sentences.add(string.substring(fromIndex, toIndex));

            fromIndex = toIndex;
            //System.out.println(sentences.size());
        }
        //this will check if a word is not the part of another word
        String patternStr = ".*?\\b" + str + "\\b.*?";
        //choose sentences containing given words
        System.out.println("pattern: " + patternStr);
        Pattern pattern;
        for (String s : sentences) {
            pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(s);

            if (s.toLowerCase().contains(str) && matcher.matches()) {
                System.out.println(s);
            }
        }
        //&& Pattern.matches(patternStr, s.toLowerCase())

    }
}
