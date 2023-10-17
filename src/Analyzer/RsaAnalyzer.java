package Analyzer;

import Analyzer.Interface.AnalyzerAlg;

import java.math.BigInteger;

public class RsaAnalyzer implements AnalyzerAlg {

    BigInteger d, z, p, q, n, e, cipher;

    public RsaAnalyzer(String arg,BigInteger e,BigInteger n,String cipher){
        this.n = n;
        this.e = e;
        this.cipher = new BigInteger(cipher);
        if(arg.charAt(0) == 'd'){
            d = new BigInteger(arg.substring(1));
        }
        else if(arg.charAt(0) == 'z'){
            z = new BigInteger(arg.substring(1));
            System.out.println(z);
            d = e.modInverse(z);
            System.out.println(d);
        }
        else if(arg.charAt(0) == 'p'){
            int q_pos = arg.indexOf('q');
            p = new BigInteger(arg.substring(1,q_pos));
            q = new BigInteger(arg.substring(q_pos+1));
            z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
            d = e.modInverse(z);
        }
    }

    @Override
    public void analyze() {
        int D = d.intValue();
        System.out.println((cipher.pow(D)).mod(n));
    }
}
