package Analyzer;

public class VigenereAnalyzer implements AnalyzerAlg {
    private String ciphertext;

    public VigenereAnalyzer(String ciphertext) {
        this.ciphertext = ciphertext.toUpperCase(); // Convert ciphertext to uppercase for consistency
    }

    @Override
    public void analyze() {
        int[] letterFrequency = new int[26]; // Array to store letter frequencies

        // Calculate letter frequencies
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                letterFrequency[c - 'A']++; // Increment the corresponding letter's frequency
            }
        }

        // Print letter frequencies
        System.out.println("Letter Frequencies in Ciphertext:");
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            int frequency = letterFrequency[letter - 'A'];
            System.out.println(letter + ": " + frequency);
        }
    }
}
