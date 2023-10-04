package Algorithm;
import java.util.Scanner;

public class caesarcypher {
    
    /*
     * the main part allows the user to create its own plain text to decrypt 
     * and a key(interger) that is used to help encrypt and decrypt the 
     * cryptosystem. This system is called a caesar cypher cryptosystem with an 
     * encryption decryption implementation 
     */
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        //Allows the user to write a plain text to encrypt
        System.out.print("Enter plain text: "); 
        String plaintext = scanner.nextLine();

        //allows the user to write a designated key
        System.out.print("Enter a Integer key: ");
        int key = scanner.nextInt();

        // Encrypts it using the caesar cypher method
        String Encryptedtext = Encrypt(plaintext, key);
        System.out.print("Encrypted Text: " + Encryptedtext);

        // Decrypts the encrypted text.
        String Decryptedtext = Decrypt(Encryptedtext, key);
        System.out.print("    Decrypted Text: " + Decryptedtext);

        scanner.close();

    }
    /**
     * Takes the text and key and encrypts the text using the caesar cypher
     * method, shifts the letter by the key ammount.
     * returns back the encrypted text.
     * @param plaintext
     * @param key
     * @return Encryptedtext
     */
    public static String Encrypt(String plaintext, int key){

        StringBuilder Encryptedtext = new StringBuilder();

        for(char c : plaintext.toCharArray()) { //loops through the characters in the array.
             
            if (Character.isLetter(c)) { //IF it is the letters from the text encrypt the text

                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int originalAlphabetPosition = c - base;
                int newAlphabetPosition = (originalAlphabetPosition + key) % 26;

                char encryptedChar = (char) (base + newAlphabetPosition);
                Encryptedtext.append(encryptedChar);

            } else { //append the text

                Encryptedtext.append(c); 
            }
        }

        return Encryptedtext.toString();

    }
        /**
         * takes the encrypted text and decrypts it using the caesar cypher method
         * returns with the decrypted message. 
         * @param EncryptedText
         * @param key
         * @return DecryptedText
         */
        public static String Decrypt(String EncryptedText, int key) {

        StringBuilder DecryptedText = new StringBuilder();
        
        for (char c : EncryptedText.toCharArray()) { //loops through the characters in the array.

            if (Character.isLetter(c)) { //IF it is the encrypted text THEN decrypt the text
                

                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int originalAlphabetPosition = c - base;
                int newAlphabetPosition = (originalAlphabetPosition - key + 26) % 26;


                char decryptedChar = (char) (base + newAlphabetPosition);
                DecryptedText.append(decryptedChar);

            } else { //ELSE append the text 

                DecryptedText.append(c);

            }
        }
        
        return DecryptedText.toString();

    }

}

