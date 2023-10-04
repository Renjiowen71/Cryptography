package Algorithm;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Rsa implements AsyCryptAlg{
    int p,q,n,z,e,d;
    public Rsa(){
        d = 0;
        p = 5;
        q = 97;
        n = p*q;
        z = (p-1)*(q-1);
        for (e = 2; e < z; e++) {

            if (gcd(e, z) == 1) {
                break;
            }
        }
        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * z);

            if (x % e == 0) {
                d = x / e;
                break;
            }
        }
    }

    @Override
    public String encrypt(String publicKey, String message) {
        String[] s = publicKey.split(",");
        int n = Integer.parseInt(s[0]);
        int e = Integer.parseInt(s[1]);
        int m = Integer.parseInt(message);
        double r = ((Math.pow(m,e)) % n);
        System.out.println("d:"+d+" z:"+z+" p:"+p+" q:"+q);
        return Double.toString(r);
    }

    @Override
    public String decrypt(String cipher) {
        BigInteger N = BigInteger.valueOf(n);
        BigInteger C = BigDecimal.valueOf(Double.valueOf(cipher)).toBigInteger();
        BigInteger msgback = (C.pow(d)).mod(N);
        return String.valueOf(msgback);
    }

    @Override
    public String getPublicKey() {
        return Integer.toString(n) +","+ Integer.toString(e);
    }

    static int gcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }
}
