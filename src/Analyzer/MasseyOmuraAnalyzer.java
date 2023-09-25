package Analyzer;

import Algorithm.CryptAlg;
import Algorithm.MasseyOmura;

import java.math.BigInteger;

public class MasseyOmuraAnalyzer implements AnalyzerAlg {
    private BigInteger y1;
    private BigInteger y2;
    private BigInteger y3;
    private BigInteger p;

    public MasseyOmuraAnalyzer(BigInteger y1, BigInteger y2, BigInteger y3, BigInteger p) {
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        System.out.println("Y1 = "+y1);
        System.out.println("Y2 = "+y2);
        System.out.println("Y3 = "+y3);
        this.p = p;
    }

    @Override
    public void analyze(){
        BigInteger xIA=BigInteger.ZERO;
        for(BigInteger i = BigInteger.TWO;i.compareTo(p)==-1;i=i.add(BigInteger.ONE)){
            if(y2.modPow(i,p).equals(y3)){
                xIA=i;
            }
        }
        System.out.println("message = "+ MasseyOmura.decryptWithKey(y1.toString(), xIA, p));
    }

    public static void tryAnalyzer(String message){
        BigInteger p = new BigInteger("2679163");
        CryptAlg algA = new MasseyOmura(new BigInteger("234131"),p);
        CryptAlg algB = new MasseyOmura(new BigInteger("234523"),p);
        String y1 = algA.encrypt(message);
        String y2 = algB.encrypt(y1);
        String y3 = algA.decrypt(y2);
        System.out.println("Real Message = "+message);
        System.out.println("Decrypted Message = "+algB.decrypt(y3));
        AnalyzerAlg masseyOmuraAnalyze = new MasseyOmuraAnalyzer(
                new BigInteger(y1),
                new BigInteger(y2),
                new BigInteger(y3),
                p
        );
        masseyOmuraAnalyze.analyze();
    }
}
