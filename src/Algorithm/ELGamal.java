package Algorithm;

import Algorithm.Interface.AsyCryptAlg;

import java.math.BigInteger;

public class ELGamal implements AsyCryptAlg{
    private BigInteger k;
    private BigInteger x;
    private BigInteger p;
    private BigInteger a;
    private BigInteger publicKey;

    //Constructor Example AsyCryptAlg alg2 = new ELGamal(new BigInteger("3235"),new BigInteger("6345"),new BigInteger("59861821"), new BigInteger("234131"));
    public ELGamal(BigInteger k, BigInteger x, BigInteger p, BigInteger a, boolean pubKey) {
        this.k = k;
        if(pubKey){
            for(BigInteger i =BigInteger.TWO;i.compareTo(p)==-1;i = i.add(BigInteger.ONE)){
                if(a.modPow(i,p).compareTo(publicKey)==0){
                    this.x = i;
                    break;
                }
            }
        } else {
            this.x = x;
        }
        this.p = p;
        this.a = a;
    }
    public String getPublicKey(){
        if(publicKey!=null) return publicKey.toString();
        return a.modPow(x,p).toString();
    }

    @Override
    public String encrypt(String publicKey, String message) {
        BigInteger m = new BigInteger(message);
        BigInteger y = new BigInteger(publicKey);
        System.out.println("a"+a+"k"+k+"p"+p);
        BigInteger ak = a.modPow(k,p);
        System.out.println("y"+y+"k"+k+"p"+p);
        BigInteger Km = y.modPow(k,p).multiply(m).mod(p);
        return ak+","+Km;
    }

    @Override
    public String decrypt(String cipher) {
        String[] akAndKm = cipher.split(",");
        BigInteger ak = new BigInteger(akAndKm[0]);
        BigInteger Km = new BigInteger(akAndKm[1]);
        System.out.println("x"+x+"p"+p);
        BigInteger K = ak.modPow(x,p);
        System.out.println("K"+K);
        BigInteger KInverse = K.modInverse(p);
        System.out.println(Km+","+KInverse);
        BigInteger message = Km.multiply(KInverse).mod(p);
        return message.toString();
    }
}
