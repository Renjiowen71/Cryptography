package Algorithm;

import Algorithm.Interface.CryptAlg;

public class SimpleSubstitution implements CryptAlg{
    String key;

    public SimpleSubstitution(String key){
        this.key = key;
    }

    @Override
    public String encrypt(String message) {
        String output = "";
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] key_arr = key.toCharArray();
        for(int x = 0;x<message.length();x++){
            if(!(message.charAt(x) == ' ')) {
                output += key_arr[getPos(alphabet, message.charAt(x))];
            }
            else{
                output += ' ';
            }
        }
        return output;
    }

    @Override
    public String decrypt(String cipher) {
        String output = "";
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] key_arr = key.toCharArray();
        for(int x = 0;x<cipher.length();x++){
            if(cipher.charAt(x) != ' ') {
                output += alphabet[getPos(key_arr, cipher.charAt(x))];
            }
            else{
                output += ' ';
            }
        }
        return output;
    }
    private static int getPos(char[] charArr, char charNeeded){
        int found = -1;
        for (int i = 0; i < charArr.length; ++i) {
            if (charArr[i] == charNeeded) {
                found = i;
                return found;
            }
        }
        return found;
    }
}
