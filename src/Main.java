import Algorithm.*;
import Analyzer.AnalyzerAlg;
import Analyzer.ElGamalAnalyzer;
import Analyzer.MasseyOmuraAnalyzer;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
//        CryptAlg alg = new Feistel(10, "AND");
//        CryptAlg alg = new MasseyOmura(new BigInteger("234131"),new BigInteger("59861821"));
//        AsyCryptAlg alg1 = new ELGamal(new BigInteger("2341"),new BigInteger("7345"),new BigInteger("59861821"), new BigInteger("234131"));
//        AsyCryptAlg alg2 = new ELGamal(new BigInteger("3235"),new BigInteger("6345"),new BigInteger("59861821"), new BigInteger("234131"));

//        String message = "Hello World!";
//        String message = "14235";

//        tryAsyAlg(alg1, alg2, message);
//        tryAlg(alg, message);
//        ElGamalAnalyzer.tryAnalyzer(message);
//        MasseyOmuraAnalyzer.tryAnalyzer(message);
    }
    public static void tryAsyAlg(AsyCryptAlg alg1, AsyCryptAlg alg2, String message){
        String cipher = alg1.encrypt(alg2.getPublicKey(),message);
        System.out.println("======= alg1 ==> alg2 ======");
        String result = alg2.decrypt(cipher);
        System.out.println("Message = " + message);
        System.out.println("Cipher text  = " + cipher);
        System.out.println("decryption result = " + result);
        System.out.println("======= alg2 ==> alg1 ======");
        cipher = alg2.encrypt(alg1.getPublicKey(),message);
        result = alg1.decrypt(cipher);
        System.out.println("Message = " + message);
        System.out.println("Cipher text  = " + cipher);
        System.out.println("decryption result = " + result);
    }

    public static void tryAlg(CryptAlg alg, String message){
        String cipher = alg.encrypt(message);
        String result = alg.decrypt(cipher);
        System.out.println("Message = " + message);
        System.out.println("Cipher text  = " + cipher);
        System.out.println("decryption result = " + result);
    }
}