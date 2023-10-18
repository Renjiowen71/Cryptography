package Algorithm;

public class VigenereCipherBreaker {
    public static void main(String[] args) {
        // Encrypted message to be decrypted
        String encryptedMessage = "qvzf";

        // Create an instance of the VigenereCipherBreaker
        VigenereCipherBreaker breaker = new VigenereCipherBreaker();

        // Perform the exhaustive key search and print the results
        breaker.breakVigenere(encryptedMessage);
    }

    // Perform an exhaustive key search for a Vigenere cipher
    public void breakVigenere(String encryptedMessage) {
        // Define a character set that you want to consider in the key
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // You can expand this to include digits, symbols, etc.

        // Try all possible key lengths
        for (int keyLength = 1; keyLength <= 20; keyLength++) { // Adjust the upper limit as needed
            generatePossibleKeys(0, keyLength, charset, "", encryptedMessage);
        }
    }

    // Recursive method to generate all possible keys
    private void generatePossibleKeys(int currentLength, int targetLength, String charset, String currentKey, String encryptedMessage) {
        if (currentLength == targetLength) {
            // Decrypt the message using the generated key
            String decryptedMessage = decryptWithKey(encryptedMessage, currentKey);

            // Calculate the Index of Coincidence for the decrypted message
            double ic = new VigenereCipher(currentKey).calculateIC(decryptedMessage);

            // Print the key and decrypted message if IC is high (close to 0.065)
            if (ic > 0.8) {
                System.out.println("Possible Key: " + currentKey);
                System.out.println("Decrypted Message: " + decryptedMessage);
                System.out.println("Index of Coincidence (IC): " + ic);
                System.out.println();
            }
        } else {
            // Recursively generate possible keys
            for (int i = 0; i < charset.length(); i++) {
                generatePossibleKeys(currentLength + 1, targetLength, charset, currentKey + charset.charAt(i), encryptedMessage);
            }
        }
    }

    // Decrypt the message using a given key
    private String decryptWithKey(String encryptedMessage, String key) {
        VigenereCipher vigenereCipher = new VigenereCipher(key);
        return vigenereCipher.decrypt(encryptedMessage);
    }
}
