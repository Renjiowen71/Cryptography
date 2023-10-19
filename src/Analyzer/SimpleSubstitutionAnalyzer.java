package Analyzer;

import Analyzer.Interface.AnalyzerAlg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class SimpleSubstitutionAnalyzer implements AnalyzerAlg {
    String ciphertext;
    int[] frequency;
    char[] nFrequency = "etaoinshrdlcumwfgypbvkjxqz".toCharArray();
    char[] alphabet;
    char[] guess;
    ArrayList<Integer>a;
    public SimpleSubstitutionAnalyzer(String ciphertext){
        //Read in quad scores
        a = new ArrayList<Integer>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader("a.txt"));
            String line = bf.readLine();
            while (line != null) {
                a.add(Integer.valueOf(line));
                line = bf.readLine();
            }
            bf.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //Save the ciphertext
        this.ciphertext = ciphertext;
        //Generate the frequency of the ciphertext
        frequency = new int[26];
        Arrays.fill(frequency,0);
        generateFrequency();
        //Generate the alphabet
        alphabet = new char[26];
        for(int x = 0;x<26;x++){
            alphabet[x] = ((char)(97+x));
        }
    }

    @Override
    public void analyze() {
        //generate the first guess
        firstGuess();

        //Run hill climb based search
        int max = 0;
        boolean cont = true;
        while(cont) {
            cont = false;
            for (int i = 0; i < guess.length; i++) {
                for (int j = i; j < guess.length; j++) {
                    char a = guess[i];
                    char b = guess[j];
                    //swap
                    swap(a, b);
                    String out = generateOutput();
                    int c = calculate_fit(out);
                    if(c > max){
                        max = c;
                        cont = true;
                    }
                    else {
                        swap(a, b);
                    }
                }
            }
        }

        //Print result
        System.out.println(max);
        System.out.println(generateOutput());
        System.out.println(nFrequency);
        System.out.println(guess);

    }

    //Generate the frequency of each letter present in the ciphertext
    public void generateFrequency(){
        //loop through the ciphertext,adding for each instance of character
        for(char a:ciphertext.toCharArray()){
            if(!(a == ' ')){
                frequency[((int)a)-97] ++;
            }
        }
    }

    //generate the first guess based of the frequency found of each letter in the ciphertext
    public void firstGuess(){
        //Initalize variables
        guess = new char[26];
        int[] guard = new int[26];
        Arrays.fill(guard, 0);
        int max = -1;
        int pos = 0;
        //Find the order from highest to lowest from the frequncy array and map to nFrequency order
        for (int y = 0; y < frequency.length; y++) {
            max = -1;
            for (int x = 0; x < frequency.length; x++) {
                if (frequency[x] > max && guard[x] != 1) {
                    max = frequency[x];
                    pos = x;
                }
            }
            guard[pos] = 1;
            guess[y] = (char)(pos+97);
        }
    }

    //takes the key and generates the corresponding plaintext
    public String generateOutput(){
        StringBuilder out = new StringBuilder();
        for(char a:ciphertext.toCharArray()){
            if(!(a == ' ')){
                out.append(nFrequency[findPosition(a)]);
            }
            else{
                out.append(a);
            }
        }
        return out.toString();
    }

    //Find position of character c in key guess
    public int findPosition(char c){
        for(int x = 0;x<guess.length;x++){
            if(guess[x] == c){
                return x;
            }
        }
        return -1;
    }

    //Swap two letters in key
    public void swap(char first, char second){
        int first_pos = 0;
        int second_pos = 0;
        for(int x = 0;x<guess.length;x++){
            if(nFrequency[x] == first){
                first_pos = x;
            }
            if(nFrequency[x] == second){
                second_pos = x;
            }
        }
        char temp = guess[first_pos];
        guess[first_pos] = guess[second_pos];
        guess[second_pos] = temp;
    }

    //Maps each quad found in plaintext and sums up score
    public int calculate_fit(String s){
        int fitnesss = 0;
        int quad = (((int)s.charAt(0) -97) << 10) + (((int)s.charAt(1) -97) << 5) + (int)s.charAt(2) - 97;
        for(int i = 3;i<s.length();i++){
            quad = ((quad & 0x7FFF) << 5) + (int)s.charAt(i) - 97;
            fitnesss += a.get(quad);
        }
        return fitnesss;
    }
}