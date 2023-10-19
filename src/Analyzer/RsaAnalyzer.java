package Analyzer;

import Analyzer.Interface.AnalyzerAlg;

import java.math.BigInteger;
import java.util.ArrayList;

public class RsaAnalyzer implements AnalyzerAlg {

    BigInteger d, z, p, q, n, e, cipher;

    public RsaAnalyzer(String arg,BigInteger e,BigInteger n,String cipher) {
        this.n = n;
        this.e = e;
        this.cipher = new BigInteger(cipher);
        if (arg.charAt(0) == 'd') {
            d = new BigInteger(arg.substring(1));
        } else if (arg.charAt(0) == 'z') {
            z = new BigInteger(arg.substring(1));
            System.out.println(z);
            d = e.modInverse(z);
            System.out.println(d);
        } else if (arg.charAt(0) == 'p') {
            int q_pos = arg.indexOf('q');
            p = new BigInteger(arg.substring(1, q_pos));
            q = new BigInteger(arg.substring(q_pos + 1));
            z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
            d = e.modInverse(z);
        } else if (arg.charAt(0) == 'n') {

            int[] num = new int[1000000];
            for (int i = 0; i < num.length; i++) {
                num[i] = i;
            }
            ArrayList<Integer> prime = new ArrayList<>();
            for (int j = 0; j < num.length; j++) {
                boolean x = true;
                for (int i = 2; i < num[j]; i++) {
                    if (num[j] % i == 0) {
                        x = false;
                        break;
                    }
                }
                if (x) {
                    prime.add(num[j]);
                }
            }
            for (int i = 0; i < prime.size(); i++) {
                for (int j = 0; j < prime.size(); j++) {
                    if (BigInteger.valueOf(prime.get(i)).multiply(BigInteger.valueOf(prime.get(j))).compareTo(n) == 0) {
                        System.out.println("p:" + prime.get(i) + " q:" + prime.get(j));
                        this.p = BigInteger.valueOf(prime.get(i));
                        this.q = BigInteger.valueOf(prime.get(j));
                        this.z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
                        this.d = e.modInverse(z);
                    }
                }
            }
        } else if (arg.charAt(0) == 'c') {
            int N = this.n.intValue();
            int a = (int) Math.sqrt(58687709);
            boolean check = true;
            int b = 0;
            while (check) {
                b = a * a - N;
                int sr = (int) Math.sqrt(b);
                if (sr * sr == b) {
                    check = false;
                    break;
                }
                a++;
            }
            int c = a - (int) Math.sqrt(b);
            this.p = BigInteger.valueOf(c);
            this.q = BigInteger.valueOf(N / c);
            System.out.println(p);
            System.out.println(q);
            this.z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
            this.d = e.modInverse(z);
        }
    }

    @Override
    public void analyze() {
        int D = d.intValue();
        System.out.println((cipher.pow(D)).mod(n));
    }
}
