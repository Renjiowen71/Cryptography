package Algorithm;

import Algorithm.Interface.CryptAlg;

import java.math.BigInteger;
public class MasseyOmura implements CryptAlg {
    private BigInteger m;
    private BigInteger p;
    private BigInteger Akey;
    private BigInteger AInverseKey;

    //Constructor Example CryptAlg alg = new MasseyOmura(new BigInteger("234131"),new BigInteger("59861821"));
    public MasseyOmura(BigInteger Akey, BigInteger p){
        this.Akey = Akey;
        this.p = p;
        this.AInverseKey = Akey.modInverse(p.subtract(BigInteger.ONE));
    }

    @Override
    public String encrypt(String message) {
        BigInteger m = new BigInteger(message);
        return m.modPow(Akey,p).toString();
    }

    @Override
    public String decrypt(String cipher) {
        BigInteger c = new BigInteger(cipher);
        return c.modPow(AInverseKey,p).toString();
    }

    public static String decryptWithKey(String message, BigInteger key, BigInteger p) {
        BigInteger m = new BigInteger(message);
        return m.modPow(key,p).toString();
    }


}
