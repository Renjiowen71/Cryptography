package Algorithm;

import Algorithm.Interface.AsyCryptAlg;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Rsa implements AsyCryptAlg {
    BigInteger p, q, n, e, z, d;

    public Rsa(BigInteger p, BigInteger q) {
        d = BigInteger.ZERO;
        this.p = p;
        this.q = q;
        n = p.multiply(q);
        z = ((p.subtract(BigInteger.ONE))).multiply((q.subtract(BigInteger.ONE)));
        e = BigInteger.TWO;
        while (e.compareTo(z) < 0) {
            if (gcd(e, z).compareTo(BigInteger.ONE) == 0 && gcd(e, n).compareTo(BigInteger.ONE) == 0) {
                break;
            }
            e = e.add(BigInteger.ONE);
        }
        BigInteger i = BigInteger.ZERO;
        BigInteger stop = new BigInteger("20");
        while(i.compareTo(stop) < 0) {
            BigInteger x = z.multiply(i).add(BigInteger.ONE);
            System.out.println(x);
            if (x.mod(e).compareTo(BigInteger.ZERO) == 0) {
                d = x.divide(e);
                break;
            }
            i = i.add(BigInteger.ONE);
        }
    }


    @Override
    public String encrypt(String publicKey, String message) {
        String[] s = publicKey.split(",");
        int n = Integer.parseInt(s[0]);
        int e = Integer.parseInt(s[1]);
        int m = Integer.parseInt(message);
        double r = ((Math.pow(m,e)) % n);
        System.out.println("d:"+d+" z:"+z+" p:"+p+" q:"+q+" e:"+e+" n:"+n);
        return Double.toString(r);
    }

    @Override
    public String decrypt(String cipher) {
        BigInteger C = BigDecimal.valueOf(Double.valueOf(cipher)).toBigInteger();
        BigInteger msgback = (C.pow(d.intValue())).mod(n);
        return String.valueOf(msgback);
    }

    @Override
    public String getPublicKey() {
        return n +","+ e;
    }

    static BigInteger gcd(BigInteger e, BigInteger z)
    {
        if (e.compareTo(BigInteger.ZERO) == 0)
            return z;
        else
            return gcd(z.mod(e), e);
    }

}
