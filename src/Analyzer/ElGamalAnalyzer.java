package Analyzer;

import Algorithm.Interface.AsyCryptAlg;
import Algorithm.ELGamal;
import Analyzer.Interface.AnalyzerAlg;

import java.math.BigInteger;

public class ElGamalAnalyzer implements AnalyzerAlg {
    private BigInteger p;
    private BigInteger a;
    private BigInteger y;
    private BigInteger ak;
    private BigInteger Km;

    public ElGamalAnalyzer(String y, BigInteger p, BigInteger a, String cipher){
        this.y = new BigInteger(y);
        System.out.println("Public key = "+y);
        this.p = p;
        this.a = a;
        System.out.println("Cipher message = "+cipher);
        String akAndKm[] = cipher.split(",");
        this.ak = new BigInteger(akAndKm[0].trim());
        this.Km = new BigInteger(akAndKm[1].trim());
    }
    public void analyze(){
        BigInteger k = BigInteger.ZERO,m;
        for(BigInteger i = BigInteger.TWO;i.compareTo(p)==-1;i=i.add(BigInteger.ONE)){
            if(a.modPow(i,p).equals(ak)) {
                k=i;
                break;
            }
        }
        BigInteger K = y.modPow(k,p);
        BigInteger Kinverse = K.modInverse(p);
        m = Kinverse.multiply(Km).mod(p);
        System.out.println("Decrypted Message = "+m);
    }

    public static void tryAnalyzer(String message){//message = 12345
        System.out.println("Real message = "+message);
        BigInteger p = new BigInteger("59861821");
        BigInteger a = new BigInteger("234131");
        AsyCryptAlg alg1 = new ELGamal(new BigInteger("2341"),new BigInteger("7345"),p,a);
        AsyCryptAlg alg2 = new ELGamal(new BigInteger("3235"),new BigInteger("6345"),p,a);
        String alg2PublicKey = alg2.getPublicKey(); // public key = 4519028
        String cipher = alg1.encrypt(alg2PublicKey,message); // cipher = 24019872,48030646
        AnalyzerAlg elGamalAnalyzer = new ElGamalAnalyzer(alg2PublicKey,p,a,cipher);
        elGamalAnalyzer.analyze();
    }
}
