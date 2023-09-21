package Algorithm;

public interface AsyCryptAlg{

    public String encrypt(String publicKey, String message);
    public String decrypt(String cipher);
    public String getPublicKey();
}
