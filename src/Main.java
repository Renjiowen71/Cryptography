import Algorithm.CryptAlg;
import Algorithm.MasseyOmura;
import Analyzer.AnalyzerAlg;
import Analyzer.MasseyOmuraAnalyzer;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        CryptAlg alg = new MasseyOmura(new BigInteger("234131"),new BigInteger("59861821"));
        tryAlg(alg, "12345");
        tryMasseyOmuraAnalizer();
    }

    public static void tryAlg(CryptAlg alg, String message){
        String cipher = alg.encrypt(message);
        String result = alg.decrypt(cipher);
        System.out.println("message = " + message);
        System.out.println("cipher = " + cipher);
        System.out.println("result = " + result);
    }

    public static void tryMasseyOmuraAnalizer(){
        BigInteger p = new BigInteger("2679163");
        CryptAlg algA = new MasseyOmura(new BigInteger("234131"),p);
        CryptAlg algB = new MasseyOmura(new BigInteger("234523"),p);
        String message = "12345";
        String y1 = algA.encrypt(message);
        String y2 = algB.encrypt(y1);
        String y3 = algA.decrypt(y2);
        System.out.println("Real Message = "+algB.decrypt(y3));
        AnalyzerAlg masseyOmuraAnalyze = new MasseyOmuraAnalyzer(
                new BigInteger(y1),
                new BigInteger(y2),
                new BigInteger(y3),
                p
        );
        masseyOmuraAnalyze.analyze();
    }
}