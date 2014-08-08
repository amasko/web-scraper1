package main.java.com.hireright.scraper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataProcessor {
    private String string;
    // (<script).*?(/script>) - regex for script block
    // (<style).*?(/style>) - remove CSS
    // &\S*?; - special HTML characters
    private final String regex = "(<script).*?(</script>)|(<style).*?(/style>)|<.*?>|&\\S*?;";
    private final String sentenceDelimeter = "\\s{2,}|\\t|\\.\\s";
    private Map<String, Integer> freq;
    private List<String> sentences;

    public DataProcessor(String string) {
        this.string = string.replaceAll(regex, "  ");
    }

    public int charactersNumber() {
        //not sure if white space should count
        char[] array = string.replaceAll("\\s", "").toCharArray();
        return array.length;
    }

    public Integer wordsNumber(String str) {
        String[] s = string.split("\\W+");
        freq = new HashMap<String, Integer>();
        for (String word : s) {
            Integer count = freq.get(word.toLowerCase());
            if (count == null) freq.put(word.toLowerCase(), 1);
            else freq.put(word.toLowerCase(), count + 1);
        }
        return freq.get(str);
    }

    public void extractAllSentences() {
        sentences = new ArrayList<String>(); //collection will contain all sentences in the text
        String delim = "&end;; ";
        string = string.replaceAll(sentenceDelimeter, delim);
        int fromIndex = 0;
        int toIndex = 0;
        while (toIndex >= 0) {
            fromIndex = string.indexOf(delim, fromIndex);
            fromIndex += 6;
            toIndex = string.indexOf(delim, fromIndex);
            if (toIndex >= 0)
                sentences.add(string.substring(fromIndex, toIndex));
            fromIndex = toIndex;
            //System.out.println(sentences.size());
        }
    }

    public void loadToFile() {
        File out = new File("output.txt");
//        out.createNewFile()
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out, false)));//, "UTF-8"
            writer.write(string);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> extractSentence(String str) {
        //this will check if a word is not the part of another word
        String patternStr = ".*?\\b" + str + "\\b.*?";
        Pattern pattern;
        //choose sentences containing given words
        List<String> required = new ArrayList<String>();
        for (String s : sentences) {
            pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(s);
            if (s.toLowerCase().contains(str) && matcher.matches()) {
                required.add(s);
            }
        }
        return required;
    }

    public Map<String, Integer> getFreq() {
        return freq;
    }
}
