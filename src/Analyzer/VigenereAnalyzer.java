package Analyzer;

import Analyzer.Interface.AnalyzerAlg;
import Utility.IndexOfCoincedence;
import Utility.LetterFreq;
import Utility.Utility;

import java.util.ArrayList;
import java.util.Map;

public class VigenereAnalyzer implements AnalyzerAlg {
    private String cipher;
    public VigenereAnalyzer(String cipher){
        this.cipher = cipher;
    }

    @Override
    public void analyze() {
        System.out.println("Encrypted Message = "+cipher);
        ArrayList<StringBuilder> predictionGroups = predictKeyLength(cipher);
        System.out.println("Prediction for Key Length = "+predictionGroups.size());
//        inputStart(predictionGroups);
//        for (int i = 0; i<differs.length;i++) {
//            for (int j = i;j<predictionGroups.size();j++) {
//                keys[j]+=differs[i];
//            }
//        }
//        for (int key:keys) {
//            System.out.println(key);
//        }

    }

//    private void inputStart(){
//        while (true){
//            Utility.SCANNER.
//        }
//    }

    //    private StringBuilder splitAndConquer(List<StringBuilder> predictionGroups){
//        if(predictionGroups.size()==1) {
//            return predictionGroups.get(0);
//        }
//        List<StringBuilder>stringGroupLeft = predictionGroups.subList(0,predictionGroups.size()/2);
//        List<StringBuilder>stringGroupRight = predictionGroups.subList(predictionGroups.size()/2,predictionGroups.size());
//        StringBuilder sbLeft = splitAndConquer(stringGroupLeft);
//        StringBuilder sbRight = splitAndConquer(stringGroupRight);
//        StringBuilder merged = merge(sbLeft,sbRight);
//        return merged;
//    }
//    private StringBuilder merge(StringBuilder sb1, StringBuilder sb2){
//        StringBuilder sb = new StringBuilder();
//        String[] splitSb1 = sb1.toString().split(",");
//        String[] splitSb2 = sb2.toString().split(",");
//        sb.append(splitSb1[0]).append(",").append(splitSb1[1]).append(splitSb2[1]);
//        int difference = differenceKey(splitSb1[1],splitSb2[1]);
//        differs[Integer.valueOf(splitSb2[0])]=difference;
//        return sb;
//    }
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

//    private int differenceKey(String s1, String s2){
//        double minimumDistance= Double.MAX_VALUE;
//        int predictDiffKey=0;
//        for(int i=0;i<26;i++){
//            String temp = shift(s2,i);
//            double distance = 0.067 - calculateIOC(s1+temp);
//            if (minimumDistance>distance){
//                minimumDistance = distance;
//                predictDiffKey = i;
//                System.out.println("predict"+predictDiffKey);
//            }
//        }
//        return predictDiffKey;
//    }

    private String shift(String s2, int i){
        StringBuilder temp = new StringBuilder();
        for(char c:s2.toCharArray()){
            temp.append(c+i);
        }
        return temp.toString();
    }
}
