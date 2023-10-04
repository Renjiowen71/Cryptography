package Algorithm;

import Algorithm.Interface.AsyCryptAlg;

import java.math.BigInteger;

public class ELGamal implements AsyCryptAlg{
    private BigInteger k;
    private BigInteger x;
    private BigInteger p;
    private BigInteger a;
    
    //Constructor Example AsyCryptAlg alg2 = new ELGamal(new BigInteger("3235"),new BigInteger("6345"),new BigInteger("59861821"), new BigInteger("234131"));
    public ELGamal(BigInteger k, BigInteger x, BigInteger p, BigInteger a) {
        this.k = k;
        this.x = x;
        this.p = p;
        this.a = a;
    }

    public String getPublicKey(){
        return a.modPow(x,p).toString();
    }

    @Override
    public String encrypt(String publicKey, String message) {
        BigInteger m = new BigInteger(message);
        BigInteger y = new BigInteger(publicKey);
        BigInteger ak = a.modPow(k,p);
        BigInteger Km = y.modPow(k,p).multiply(m).mod(p);
        return ak+","+Km;
    }

    @Override
    public String decrypt(String cipher) {
        String[] akAndKm = cipher.split(",");
        BigInteger ak = new BigInteger(akAndKm[0]);
        BigInteger Km = new BigInteger(akAndKm[1]);
        BigInteger K = ak.modPow(x,p);
        BigInteger KInverse = K.modInverse(p);
        BigInteger message = Km.multiply(KInverse).mod(p);
        return message.toString();
    }
}
