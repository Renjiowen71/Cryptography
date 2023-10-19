package Analyzer;

import Analyzer.Interface.AnalyzerAlg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranspositionAnalyzer implements AnalyzerAlg {
    String cipher;
    char[][] cipherArray;
    ArrayList<Integer> keyLength;
    List<String> dictionary;

    int[] result;

    public TranspositionAnalyzer(String ciphertext) {
        //works out possible key length (Assumes padding is kept) (Max key length of 10)
        cipher = ciphertext;
        keyLength = new ArrayList<>();

        for (int i = 2; i < ciphertext.length(); i++) {
            if (ciphertext.length() % i == 0 && i < 10) {
                keyLength.add(i);
            }
        }
        System.out.println("Possible key lengths: "+keyLength);
        //Reads in dictionary
        dictionary = new ArrayList<String>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader("dictionary.txt"));
            String line = bf.readLine();
            while (line != null) {
                dictionary.add(line.toLowerCase());
                line = bf.readLine();
            }

            bf.close();
        } catch (Exception e) {
        }
    }


    @Override
    public void analyze() {
        int max = 0;
        //tries each key length assumed suitable
        for (int i = 0; i < keyLength.size(); i++) {
            //generates the corresponding 2d array with keylength
            generate(keyLength.get(i));
            //generates first guess with keylength
            int[] nums = new int[keyLength.get(i)];
            for (int j = 0; j < nums.length; j++) {
                nums[j] = j;
            }
            //generates all possible permutations with keylength
            ArrayList<int[]> res = permute(nums);
            for (int[] x : res) {
                int a = check(x);
                //if score is greater than previous max, save key
                if(a>max){
                    max = a;
                    result = x;
                }
            }
        }
        //generartes the output of final answer
        generate(result.length);
        print(result);
        for(int a:result) {
            System.out.print(a);
            System.out.print("-");
        }
    }
    //generate the corresponding 2d array from the ciphertext
    public void generate(int keyLength){
        cipherArray = new char[keyLength][(cipher.length())/keyLength];
        int i = 0;
        int count = 0;
        int second = 0;
        while(i < cipher.length()){
            cipherArray[count][second] = cipher.charAt(i);
            count++;
            if(count == keyLength){
                count = 0;
                second++;
            }
            i++;
        }
    }

    //uses the dictionary to check the fitness of the result
    public int check(int[] order){
        String t = "";
        for (int i = 0; i < cipher.length()/order.length; i++) {
            for (int j = 0; j < order.length; j++) {
                t += cipherArray[order[j]][i];
            }
        }
        String temp = "";
        int stop = 0;
        int sucess = 0;
        int previous = 0;
        boolean a = false;
        int x = 0;
        for (int i = 0; i < t.length(); i++) {
            temp += t.charAt(i);
            if (dictionary.contains(temp.toLowerCase())) {
                sucess++;
                temp = "";
                previous = i + 1;
                a = false;
                stop = 0;
            } else {
                stop++;
            }
            if (stop > 15) {
                if(a) {
                    previous++;
                    i = previous;
                }
                else{
                    i = previous;
                }
                temp = "";
                a = true;
                stop = 0;
            }
        }
        return sucess;
    }

    //prints out the plaintext with corresponding key
    public void print(int[] order){
        String t = "";
        for (int i = 0; i < cipher.length()/order.length; i++) {
            for (int j = 0; j < order.length; j++) {
                t += cipherArray[order[j]][i];
            }
        }
        System.out.println(t);
    }
    //Generate all permutations of an int array returning ArrayList of type int array
    static ArrayList<int[]> permute(int[] nums) {
        ArrayList<int[]> res = new ArrayList<int[]>();
        int x = nums.length - 1;
        permutations(res, nums, 0, x);
        return res;
    }
    static void swap(int nums[], int l, int i) {
        int temp = nums[l];
        nums[l] = nums[i];
        nums[i] = temp;
    }
    static void permutations(ArrayList<int[]> res, int[] nums, int l, int h) {
        if (l == h) {
            res.add(Arrays.copyOf(nums, nums.length));
            return;
        }
        for (int i = l; i <= h; i++) {
            // Swapping
            swap(nums, l, i);
            permutations(res, nums, l + 1, h);
            swap(nums, l, i);
        }
    }
}
