package Algorithm;

import java.util.HashMap;
import java.util.Map;

import Algorithm.Interface.CryptAlg;

public class VigenereCipher implements CryptAlg {
    private String key;

    // Constructor that initializes the VigenereCipher with a given key
    public VigenereCipher(String key) {
        this.key = key.toUpperCase(); // Convert the key to uppercase for consistency
    }

    // Utility method to remove spaces and convert to uppercase
    public static String removeSpacesAndCapitals(String text) {
        return text.replaceAll(" ", "").toUpperCase();
    }

    @Override
    // Encrypt a given message using the Vigenere cipher
    public String encrypt(String message) {
        StringBuilder ciphertext = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < message.length(); i++) {
            char plainChar = message.charAt(i);
            if (Character.isLetter(plainChar)) {
                char keyChar = key.charAt(i % keyLength);
                int shift = keyChar - 'A';
                char encryptedChar = (char) ('A' + (plainChar + shift - 'A') % 26);
                ciphertext.append(encryptedChar);
            } else {
                // Non-alphabetic characters are not encrypted
                ciphertext.append(plainChar);
            }
        }

        return ciphertext.toString();
    }

    @Override
    // Decrypt a given cipher text using the Vigenere cipher
    public String decrypt(String cipher) {
        StringBuilder plaintext = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < cipher.length(); i++) {
            char cipherChar = cipher.charAt(i);
            if (Character.isLetter(cipherChar)) {
                char keyChar = key.charAt(i % keyLength);
                int shift = keyChar - 'A';
                char decryptedChar = (char) ('A' + (cipherChar - shift - 'A' + 26) % 26);
                plaintext.append(decryptedChar);
            } else {
                // Non-alphabetic characters are not decrypted
                plaintext.append(cipherChar);
            }
        }

        return plaintext.toString();
    }

    // Calculate the Index of Coincidence (IC) for a given text
    public double calculateIC(String text) {
        int[] frequency = new int[26];
        int total = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                frequency[c - 'A']++;
                total++;
            }
        }

        double ic = 0.0;
        double totalDbl = (double) total;
        
        for (int f : frequency) {
            ic += (f * (f - 1)) / (totalDbl * (totalDbl - 1));
        }

        return ic;
    }


    // Calculate the letter frequencies for a given text
    public Map<Character, Double> calculateLetterFrequencies(String text) {
        int[] frequency = new int[26];
        int total = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                frequency[c - 'A']++;
                total++;
            }
        }

        Map<Character, Double> frequencies = new HashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            frequencies.put(c, frequency[c - 'A'] / (double) total);
        }

        return frequencies;
    }
}
