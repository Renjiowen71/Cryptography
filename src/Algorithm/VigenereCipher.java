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
        int keyIndex = 0;
    
        for (int i = 0; i < message.length(); i++) {
            char plainChar = message.charAt(i);
            if (Character.isLetter(plainChar)) {
                char keyChar = Character.toUpperCase(key.charAt(keyIndex % keyLength));
                int shift = keyChar - 'A';
                char encryptedChar = (char) (Character.isUpperCase(plainChar) ?
                                               ('A' + (plainChar + shift - 'A') % 26) :
                                               ('a' + (plainChar + shift - 'a') % 26));
                ciphertext.append(encryptedChar);
                keyIndex++;
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
        int keyIndex = 0;
    
        for (int i = 0; i < cipher.length(); i++) {
            char cipherChar = cipher.charAt(i);
            if (Character.isLetter(cipherChar)) {
                char keyChar = Character.toUpperCase(key.charAt(keyIndex % keyLength));
                int shift = keyChar - 'A';
                char decryptedChar = (char) (Character.isUpperCase(cipherChar) ?
                                               ('A' + (cipherChar - 'A' - shift + 26) % 26) :
                                               ('a' + (cipherChar - 'a' - shift + 26) % 26));
                plaintext.append(decryptedChar);
                keyIndex++;
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
                char upperC = Character.toUpperCase(c); // Convert to uppercase
                frequency[upperC - 'A']++;
                total++;
            }
        }

        double ic = 0.0;
        double totalDbl = (double) total;

        for (int f : frequency) {
            ic += (f * (f - 1)) / (totalDbl * (totalDbl - 1));
        }

        // Format IC to 4 decimal places
        return Math.round(ic * 10000.0) / 10000.0;
    }

    // Calculate the letter frequencies for a given text, considering only the letters that appear
    public Map<Character, String> calculateLetterFrequencies(String text) {
        int[] frequency = new int[26];
        int total = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char upperC = Character.toUpperCase(c); // Convert to uppercase
                frequency[upperC - 'A']++;
                total++;
            }
        }

        Map<Character, String> frequencies = new HashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            int freq = frequency[c - 'A'];
            if (freq > 0) {
                // Format frequency as a percentage without decimal places
                String formattedFreq = String.format("%d%%", Math.round((freq / (double) total) * 100));
                frequencies.put(c, formattedFreq);
            }
        }

        return frequencies;
    }

    public static void main(String[] args) {
        // Create a VigenereCipher instance with a key
        VigenereCipher vigenereCipher = new VigenereCipher("KEY");
    
        // Message to be encrypted
        String originalMessage = "HELLO WORLD";
    
        // Encrypt the message
        String encryptedMessage = vigenereCipher.encrypt(originalMessage);
        System.out.println("Original Message: " + originalMessage);
        System.out.println("Encrypted Message: " + encryptedMessage);
    
        // Decrypt the message
        String decryptedMessage = vigenereCipher.decrypt(encryptedMessage);
        System.out.println("Decrypted Message: " + decryptedMessage);
    
        // Calculate Index of Coincidence (IC) with 4 decimal places
        double ic = vigenereCipher.calculateIC(encryptedMessage);
        System.out.printf("Index of Coincidence (IC): %.4f%n", ic);
    
        // Calculate letter frequencies
        Map<Character, String> letterFrequencies = vigenereCipher.calculateLetterFrequencies(encryptedMessage);
        System.out.println("Letter Frequencies:");
        for (Map.Entry<Character, String> entry : letterFrequencies.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }    

}

