package Algorithm;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *  this caesar cypher cryptosystem encrypts a normal text by shifting the letter by 
 *  the interger number found on the key. Once the key is utilized the number the key shifts 
 *  the normal text encypting and decrypting it by the reverse process.
 */
public class caesarcypher {
    
    /**
     * the encrypt message stores the characters array of alphabetical letters
     * it then loops through the letters in the original text and shifts it by 
     * the integer key.
     * @param text
     * @param shift
     * @return result
     */
    public static String encrypt(String text, int shift) {

            StringBuilder result = new StringBuilder(); 

            for (char c : text.toCharArray()) { //FOR every letter in the array
                
                if (Character.isLetter(c) && Character.isLowerCase(c)) { //IF letter matches the message 

                    char shifted = (char) ('a' + (c - 'a' + shift) % 26); //Shift the letter by the integer key

                    result.append(shifted); //print the result

                } else { //ELSE append the text

                    result.append(c);
                }
            }
            return result.toString();
        }
    
        /**
         * the decrypt message is just reversing the function.
         * @param text
         * @param shift
         * @return decrypt message.
         */
        public static String decrypt(String text, int shift) {

            return encrypt(text, 26 - shift); 

        }
    
        /**
         * ask the users for the text aswell as the integer key used for encryption
         * and decryptions, integer key must range between 1 and 25
         * @param args
         */
        public static void main(String[] args) {
            
            Scanner scanner = new Scanner(System.in); //set variables for user interface

            String originalText = "jhlzhyjfwoly"; // original text 
            
            System.out.print("Enter the integer key: "); //the key
            int shiftAmount = scanner.nextInt();
    
            if (shiftAmount < 1 || shiftAmount > 25) { //IF it is not between the value range show message
                System.out.println("Invalid key. Please use a number between 1 and 25.");
                return;
            }

            if(shiftAmount == 19){ //IF it is the right integer show output (FOR THE GAME PURPOSE ONLY)
                
                System.out.println("YYAAAAAAAAAAAAAAAAAAYYYYYYYY!!!!!!!!!!");

                String encryptedText = encrypt(originalText, shiftAmount);
                System.out.println("Encrypted: " + encryptedText);
        
                String decryptedText = decrypt(encryptedText, shiftAmount);
                System.out.println("Decrypted: " + decryptedText);
    
                double ioc = calculateIoC(originalText); 
                System.out.println("Index of Coincidence: " + ioc);


            } else {

                System.out.println("SOOORRRYYY TRY AGAIN");

            }


        }
        /**
         * Method use to calculate the the Index of coincidence 
         * by the letters shown the the original text, it then
         * calculates by using the IOC formula.
         * @param originalText
         * @return
         */
        private static double calculateIoC(String originalText) {
            
            // Remove non-alphabetic characters and convert to uppercase
            originalText = originalText.replaceAll("[^a-zA-Z]", "").toUpperCase();

            int totalCharacters = originalText.length();

            if (totalCharacters <= 1) {
                return 0.0; // IoC is undefined for texts with 0 or 1 characters
            }

            Map<Character, Integer> characterCounts = new HashMap<>();

            for (char c : originalText.toCharArray()) { //FOR every letter in the original text
                characterCounts.put(c, characterCounts.getOrDefault(c, 0) + 1);
            }

                double ioc = 0.0;
        
            for (char c : characterCounts.keySet()) { //FOR every letter pair in the text

                int frequency = characterCounts.get(c);
                ioc += (frequency * (frequency - 1)) / (double)(totalCharacters * (totalCharacters - 1));
            }

            return ioc;
        }
    }




