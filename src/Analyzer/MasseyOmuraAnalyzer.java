package Analyzer;

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
}
