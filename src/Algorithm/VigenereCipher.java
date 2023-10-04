package Algorithm;

import Algorithm.Interface.CryptAlg;

public class VigenereCipher implements CryptAlg {
    private String key;

    public VigenereCipher(String key) {
        this.key = key.toUpperCase(); // Convert the key to uppercase for consistency
    }

    @Override
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
}
