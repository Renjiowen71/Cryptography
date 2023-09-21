package Analyzer;

import java.math.BigInteger;

public class ElGamalAnalyzer implements AnalyzerAlg{
    private BigInteger p;
    private BigInteger a;
    private BigInteger y;
    private BigInteger ak;
    private BigInteger Km;

    public ElGamalAnalyzer(String y, BigInteger p, BigInteger a, String cipher){
        this.y = new BigInteger(y);
        this.p = p;
        this.a = a;
        String akAndKm[] = cipher.split(",");
        this.ak = new BigInteger(akAndKm[0]);
        this.Km = new BigInteger(akAndKm[1]);
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
        System.out.println("message = "+m);
    }
}
