package Utility;

import java.util.HashMap;
import java.util.Map;

public class LetterFreq {

    public static int countTotalChar(String text){
        return text.replaceAll("\\s+", "").length();
    }
    public static Map countLetterFreq(String text){

        // Create a map to store letter frequencies
        Map<Character, Integer> letterFreq = new HashMap<>();

        // Count letter frequencies and total characters
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                c = Character.toUpperCase(c);
                letterFreq.put(c, letterFreq.getOrDefault(c, 0) + 1);
            }
        }
        return letterFreq;
    }

    public static void showLetterFreq(Map<Character, Integer> letterFreq, int totalChar){
        letterFreq.forEach((character, value) -> System.out.println("Char: " + character + ", Freq: " + (double)value));
    }


}
