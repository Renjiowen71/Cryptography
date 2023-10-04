package Analyzer;

import Analyzer.Interface.AnalyzerAlg;

import java.math.BigInteger;

public class RsaAnalyzer implements AnalyzerAlg {

    BigInteger d;
    BigInteger z;
    BigInteger p;
    BigInteger q;
    BigInteger n;
    BigInteger cipher;

    public RsaAnalyzer(String arg,BigInteger e,String cipher){
        this.n = e;
        this.cipher = new BigInteger(cipher);
        if(arg.charAt(0) == 'd'){
            d = new BigInteger(arg.substring(1));
        }
        else if(arg.charAt(0) == 'z'){
            z = new BigInteger(arg.substring(1));
            d = e.modInverse(z);
        }
        else if(arg.charAt(0) == 'p'){
            int q_pos = arg.indexOf('q');
            p = new BigInteger(arg.substring(1,q_pos));
            q = new BigInteger(arg.substring(q_pos+1));
            BigInteger one = new BigInteger("1");
            z = (p.subtract(one)).multiply(q.subtract(one));
            d = e.modInverse(z);
        }
    }

    @Override
    public void analyze() {
        int D = d.intValue();
        System.out.println((cipher.pow(D)).mod(n));
    }
}
