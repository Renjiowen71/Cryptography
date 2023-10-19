package Analyzer;

import Analyzer.Interface.AnalyzerAlg;
import Utility.IndexOfCoincedence;
import Utility.LetterFreq;
import Utility.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class VigenereAnalyzer implements AnalyzerAlg {
    private int[] keys;
    private String cipher;
    public VigenereAnalyzer(String cipher){
        this.cipher = cipher;
    }

    @Override
    public void analyze() {
        System.out.println("Encrypted Message = "+cipher);
        ArrayList<StringBuilder> predictionGroups = predictKeyLength(cipher);
        System.out.println("Prediction for Key Length = "+predictionGroups.size());
        keys = new int[predictionGroups.size()];
        inputStart(predictionGroups.size(), cipher);
    }
    public void inputStart(int keyLength, String cipher){
        String cleanText = cleanText(cipher);
        try {
            while (true) {
                System.out.println("Shifting position");
                int shiftPosition = Utility.SCANNER.nextInt();
                System.out.println("Number of shifting");
                int shiftNumber = Utility.SCANNER.nextInt();
                keys[shiftPosition % keyLength] += shiftNumber;
                cleanText = shift(cleanText, shiftPosition, shiftNumber, keyLength);
                System.out.println(cleanText);
                System.out.println("Try Again?(Y/N)");
                if (Utility.SCANNER.nextLine().contains("N")) {
                    break;
                }
            }
        }catch (Exception e){
            System.out.print("keys = [");
            for (int key:keys) {
                System.out.print(key+", ");
            }
            System.out.println("]");

        }

    }
    private ArrayList<StringBuilder> predictKeyLength(String cipher){
        ArrayList<StringBuilder> stringBuilders, predictStringGroups = new ArrayList<>();
        double minimumDistance = Double.MAX_VALUE, distance;
        for(int totalGroup = 1; totalGroup<11;totalGroup++) {
            double totalIOC=0;
            stringBuilders = splitIntoGroups(cipher, totalGroup);
            for (StringBuilder stringBuilder : stringBuilders) {
                totalIOC += calculateIOC(stringBuilder.toString());
            }
            distance = 0.067 - totalIOC/totalGroup;
            if (minimumDistance>distance){
                minimumDistance = distance;
                predictStringGroups = stringBuilders;
            }
        }
        return predictStringGroups;
    }

    private ArrayList<StringBuilder> splitIntoGroups(String text, int totalGroup){
        String cleanText = cleanText(text);
        ArrayList<StringBuilder> stringGroups = new ArrayList<>();
        for (int i=0;i<totalGroup;i++){
            stringGroups.add(new StringBuilder());
        }
        for (int i =0;i<cleanText.length();i++){
            stringGroups.get(i%totalGroup).append(cleanText.charAt(i));
        }
        return  stringGroups;
    }

    private String cleanText(String text){
        return text.replaceAll("[^a-zA-Z0-9_-]", "");
    }
    private double calculateIOC(String text){
        int totalChar = LetterFreq.countTotalChar(text);
        Map<Character,Integer> letterFreq = LetterFreq.countLetterFreq(text);
        double iocValue = IndexOfCoincedence.calculateIOC(letterFreq, totalChar);
        //LetterFreq.showLetterFreq(letterFreq,totalChar);
        return iocValue;
    }

    private String shift(String cipher, int shiftPosition, int shiftNumber, int keyLength){
        StringBuilder temp = new StringBuilder();
        for(int i =0; i< cipher.length();i++){
            if(shiftPosition%keyLength==i%keyLength){
                char shifted = (char) ('a' + (cipher.charAt(i) - 'a' + shiftNumber) % 26);
                temp.append(shifted);
            } else {
                temp.append(cipher.charAt(i));
            }
        }
        return temp.toString();
    }
}
