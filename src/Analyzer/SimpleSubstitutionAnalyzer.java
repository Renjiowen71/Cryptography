package Analyzer;

import java.util.*;

public class SimpleSubstitutionAnalyzer implements AnalyzerAlg{
    String c;
    int[] frequency;
    ArrayList<Character> alphabet;
    ArrayList<Character> Nfrequency;
    Map<String, Integer> tripples;
    char[] guess;
    int total;
    Scanner userInput;

    public SimpleSubstitutionAnalyzer(String c){
        //Initalize variables
        this.c = c;
        frequency = new int[26];
        alphabet = new ArrayList<Character>();
        Nfrequency = new ArrayList<Character>();
        guess = new char[26];
        userInput = new Scanner(System.in);
        //Count frequency of each letter in ciphertext
        for(int x = 0;x<26;x++){
            frequency[x] = 0;
        }
        for(char a:this.c.toCharArray()){
            if(!(a == ' ' || a == '-')) {
                frequency[((int) a) - 97] += 1;
            }
        }
        for(int i:frequency){
            System.out.println(i);
        }

        //initialize alphabet order + English natural frequency order
        for(int x = 0;x<26;x++){
            alphabet.add((char)(97+x));
        }
        String freq = "etaoinshrdlcumwfgypbvkjxqz";
        for(char a :freq.toCharArray()){
            Nfrequency.add(a);
        }

        //generate the first guess, based on english natural frequency
        int[] guard = new int[26];
        Arrays.fill(guard, 0);
        int max = -1;
        int pos = 0;
        for (int y = 0; y < frequency.length; y++) {
            max = -1;
            for (int x = 0; x < frequency.length; x++) {
                if (frequency[x] > max && guard[x] != 1) {
                    max = frequency[x];
                    pos = x;
                }
            }
            guard[pos] = 1;
            guess[pos] = Nfrequency.get(y);
        }

    }


    @Override
    public void analyze() {
        while(true) {
            System.out.println(guess);
            System.out.println("abcdefghijklmnopqrstuvwxyz");
            String d = generate();
            System.out.println(d);
            System.out.println(this.c);
            total = 0;
            tripples = new HashMap<String, Integer>();
            generateTripples(d);
            String input = userInput.nextLine();
            if(input.length()  > 2){
                if(input.charAt(2) == 'q') {
                    break;
                }
            }
            swap(input);
        }
    }

    public String generate(){
        StringBuilder r = new StringBuilder();
        for(char a:c.toCharArray()){
            if(!(a == ' ' || a == '-')) {
                r.append(guess[a - 97]);
            }
            else{
                r.append(' ');
            }
        }
        return r.toString();
    }

    public void generateTripples(String c) {
        total = 0;
        String temp = "";
        for (char a : c.toCharArray()) {
            if (a == ' ') {
                if (temp.length() == 3) {
                    if (tripples.get(temp) == null) {
                        tripples.put(temp, 1);
                    } else {
                        int b = tripples.get(temp);
                        tripples.remove(temp);
                        tripples.put(temp, b + 1);
                    }
                }
                temp = "";
            } else {
                temp += a;
            }
        }
        if (temp.length() == 3) {
            if (tripples.get(temp) == null) {
                tripples.put(temp, 1);
            } else {
                int b = tripples.get(temp);
                tripples.remove(temp);
                tripples.put(temp, b + 1);
            }
        }
        for (Map.Entry<String, Integer> me :
                tripples.entrySet()) {
            total += me.getValue();
        }
        for (Map.Entry<String, Integer> me :
                tripples.entrySet()) {

            // Printing keys
            System.out.print(me.getKey() + ":");
            System.out.println(me.getValue()*100/total+"%");

        }
    }
    public void swap(String s){
        char first = s.charAt(0);
        char second = s.charAt(1);
        int first_pos = 0;
        int second_pos = 0;
        for(int x = 0;x<guess.length;x++){
            if(guess[x] == first){
                first_pos = x;
            }
            if(guess[x] == second){
                second_pos = x;
            }
        }
        guess[first_pos] = second;
        guess[second_pos] = first;
    }

}