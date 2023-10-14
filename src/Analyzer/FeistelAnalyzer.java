package Analyzer;

import Algorithm.Feistel;
import Analyzer.Interface.AnalyzerAlg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FeistelAnalyzer implements AnalyzerAlg {
    private static String KEYS[] = {
            "0000",
            "0001",
            "0010",
            "0011",
            "0100",
            "0101",
            "0110",
            "0111",
            "1000",
            "1001",
            "1010",
            "1011",
            "1100",
            "1101",
            "1110",
            "1111",
    };
    private String cipher;
    private int totalRound;
    private String functionOperator;
    private ArrayList<String> keys = new ArrayList<>();
    private HashMap<ArrayList<String>, String> possibleResult = new HashMap<>();
    private int possibleKeysMax;
    private int currentPossibleKeys;
    private final boolean printResult = true;
    public FeistelAnalyzer(String cipher, int totalRound, String functionOperator, int possibleKeysMax){
        this.cipher = cipher;
        System.out.println("Cipher message = "+cipher);
        this.totalRound = totalRound;
        this.functionOperator = functionOperator;
        this.possibleKeysMax = possibleKeysMax;
        this.currentPossibleKeys = 0;
    }
    @Override
    public void analyze() {
        currentPossibleKeys=0;
        String[] functionOperators=functionOperator.split(",");
        for(int i = 0;i<functionOperators.length;i++){
            functionOperator = functionOperators[i];
            currentPossibleKeys=0;
            System.out.println("Function Operator = "+functionOperator);
            if(printResult)System.out.println("Possible Message, Key:");
            buildKeyandAnalyze(0);
            ArrayList<HashMap<Character, AtomicLong>> allCharCounts = printPossibleResultAndCountChar();
            ArrayList<ArrayList<Character>>  predictionSentence = predict(allCharCounts);
            printFinalPrediction(predictionSentence);
        }
    }

    private ArrayList<HashMap<Character, AtomicLong>> printPossibleResultAndCountChar() {
        ArrayList<HashMap<Character, AtomicLong>> allCharCounts = new ArrayList<>();
        possibleResult.forEach((key, message)->{
            for(int i =0; i<message.length();i++){
                if(allCharCounts.size()<i+1) allCharCounts.add(new HashMap<Character, AtomicLong>());
                allCharCounts.get(i).putIfAbsent(message.charAt(i), new AtomicLong(0));
                long temp = allCharCounts.get(i).get(message.charAt(i)).incrementAndGet();
            }
        });
        return allCharCounts;
    }

    private ArrayList<ArrayList<Character>>  predict(ArrayList<HashMap<Character, AtomicLong>> allCharCounts) {
        System.out.println("Predicting....");
        ArrayList<ArrayList<Character>>predictionSentence = new ArrayList<>();
        AtomicLong i = new AtomicLong(0);
        allCharCounts.forEach((charCounts)->{
            AtomicLong maxCount = new AtomicLong(0);
            predictionSentence.add(new ArrayList<Character>());
            charCounts.forEach((character, count)->{
                long maxCountLong = maxCount.get();
                if(maxCountLong<count.get()){
                    predictionSentence.remove(predictionSentence.size()-1);
                    predictionSentence.add(new ArrayList<Character>());
                    predictionSentence.get(i.intValue()).add(character);
                    maxCount.compareAndSet(maxCountLong,count.get());
                } else if(maxCountLong==count.get()){
                    predictionSentence.get(i.intValue()).add(character);
                }
            });
            i.incrementAndGet();
        });
        return predictionSentence;
    }
    
    private void printFinalPrediction(ArrayList<ArrayList<Character>> predictionSentence){
        System.out.println("Final Prediction :");
        predictionSentence.forEach((characters)->{
            if(characters.size()==1){
                System.out.print(characters.get(0));
            } else {
                System.out.print("(");
                characters.forEach((character -> {
                    System.out.print(character);
                }));
                System.out.print(")");
            }
        });
        System.out.println();
    }

    public void buildKeyandAnalyze(int currentRound){
        if(possibleKeysMax==currentPossibleKeys)return;
        if(currentRound==totalRound){
            getMessage();
        } else {
            addKey(currentRound);
        }
    }
    public void addKey(int currentRound){
        for(int i=0;i<KEYS.length;i++) {
            keys.add(KEYS[i]);
            buildKeyandAnalyze(currentRound+1);
            keys.remove(keys.size()-1);
        }
    }
    public void getMessage(){
        String plainBinary;
        char resultChar;
        Feistel feistel = new Feistel(keys, functionOperator);
        boolean success = true;
        StringBuilder sb = new StringBuilder();
        for (char ch : cipher.toCharArray()) {
            plainBinary = feistel.decryptChar(ch);
            resultChar = (char) Integer.parseInt(plainBinary, 2);
            if (isOkay(resultChar)) {
                sb.append(resultChar);
            } else {
                success = false;
                break;
            }
        }
        if (success){
            possibleResult.put((ArrayList<String>)keys.clone(),sb.toString());
            if(printResult) {
                System.out.print("(" + sb.toString() + ")");
                System.out.print(", {");
            }
            if(printResult) {
                keys.forEach((k) -> {
                    System.out.print("(");
                    System.out.print(k);
                    System.out.print(")");
                });
                System.out.println("}");
            }
            currentPossibleKeys++;
        }
    }

    public static void tryAnalyzer(String message){
        int totalRound = 7;
        int possibleKeysMax = 9999;
        String functionOperator = "or";
        Feistel feistel = new Feistel(totalRound, functionOperator);
        String cipher = feistel.encrypt(message);
        FeistelAnalyzer feistelAnalyzer = new FeistelAnalyzer(cipher, totalRound, functionOperator, possibleKeysMax);
        feistelAnalyzer.analyze();
    }

    private static boolean isOkay(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9') ||
                (c == ' ')|| (c == '.') ||
                (c == ',');
    }

}
