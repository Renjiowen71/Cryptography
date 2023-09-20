package Algorithm;

import java.util.ArrayList;
public class Feistel implements  CryptAlg{
    private String functionOperator;
    private int totalRound;
    private ArrayList<String> keys = new ArrayList<>();

    public Feistel(int round, String functionOperator) {
        this.functionOperator = functionOperator.toUpperCase();
        this.totalRound = round;
        for (int i = 0; i < totalRound; i++){
            keys.add(
                    Math.round(Math.random()) + "" + Math.round(Math.random()) + "" + Math.round(Math.random()) + "" + Math.round(Math.random())
            );
        }
    }


    public Feistel(int round, String functionOperator, ArrayList keys) {
        this.functionOperator = functionOperator.toUpperCase();
        this.totalRound = round;
        this.keys = keys;
    }
    @Override
    public String encrypt(String message){
        StringBuilder sb = new StringBuilder();
        for (char ch: message.toCharArray()) {
            String cipherBinary=encryptChar(ch);
            sb.append((char) Integer.parseInt(cipherBinary, 2));
        }
        return sb.toString();
    }
    private String encryptChar(char character) {
        String characterBinary = String.format("%08d", Integer.parseInt(Integer.toString(character, 2)));
        String left = characterBinary.substring(0, 4);
        String right = characterBinary.substring(4);

        for (int roundIndex = 0; roundIndex < totalRound; roundIndex++) {
            String temp = right;
            String functionResult = function(right, roundIndex);
            right = XOR(left, functionResult);
            left = temp;
        }
        return left + "" + right;
    }
    @Override
    public String decrypt(String cipher){
        StringBuilder sb = new StringBuilder();
        for (char ch: cipher.toCharArray()) {
            String plainBinary=decryptChar(ch);
            sb.append((char) Integer.parseInt(plainBinary, 2));
        }
        return sb.toString();
    }

    private String decryptChar(char encryptedCharacter) {
        String encryptedBinary = String.format("%08d", Integer.parseInt(Integer.toString(encryptedCharacter, 2)));
        String left = encryptedBinary.substring(0, 4);
        String right = encryptedBinary.substring(4);

        for (int roundIndex = 0; roundIndex < totalRound; roundIndex++) {
            String temp = left;
            String functionResult = function(left, totalRound - roundIndex - 1);
            left = XOR(right, functionResult);
            right = temp;
        }

        return left + "" + right;
    }

    private String function(String right, int roundIndex) {
        String currentKey = keys.get(roundIndex%keys.size());
        String encryptedText = "";
        switch (functionOperator) {
            case "AND":
                encryptedText = AND(right, currentKey);
                break;
            default:
                encryptedText = OR(right, currentKey);
                break;
        }
        return encryptedText;
    }

    private String AND(String left, String right) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < left.length(); i++) {
            stringBuilder.append((left.charAt(i) - '0') & (right.charAt(i) - '0'));
        }
        return stringBuilder.toString();
    }

    private String OR(String left, String right) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < left.length(); i++) {
            stringBuilder.append((left.charAt(i) - '0') | (right.charAt(i) - '0'));
        }
        return stringBuilder.toString();
    }

    private String XOR(String left, String right) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < left.length(); i++) {
            stringBuilder.append((left.charAt(i) - '0') ^ (right.charAt(i) - '0'));
        }
        return stringBuilder.toString();
    }

}
