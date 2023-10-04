import Algorithm.Interface.AsyCryptAlg;
import Algorithm.Interface.CryptAlg;
import Algorithm.caesarcypher;

public class TryAlgorithm {
    public static void run(){
        try{
            Object algObj = ClassUtility.getObj("Algorithm");
            if(algObj instanceof CryptAlg){
                CryptAlg alg = (CryptAlg) algObj;
                String message = Utility.run();
                tryAlg(alg, message);
            } else if (algObj instanceof AsyCryptAlg) {
                AsyCryptAlg alg1 = (AsyCryptAlg) algObj;
                System.out.println("Creating second ");
                AsyCryptAlg alg2 = (AsyCryptAlg) ClassUtility.initClass(alg1.getClass().getName());
                String message = Utility.run();
                tryAsyAlg(alg1, alg2, message);
            } else if (algObj.getClass()== caesarcypher.class){
                caesarcypher caesarCypher= (caesarcypher) algObj;
                caesarcypher.main(null);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void tryAsyAlg(AsyCryptAlg alg1, AsyCryptAlg alg2, String message){
        String cipher = alg1.encrypt(alg2.getPublicKey(),message);
        System.out.println("======= alg1 ==> alg2 ======");
        String result = alg2.decrypt(cipher);
        System.out.println("Message = " + message);
        System.out.println("Cipher text  = " + cipher);
        System.out.println("decryption result = " + result);
        System.out.println("======= alg2 ==> alg1 ======");
        cipher = alg2.encrypt(alg1.getPublicKey(),message);
        result = alg1.decrypt(cipher);
        System.out.println("Message = " + message);
        System.out.println("Cipher text  = " + cipher);
        System.out.println("decryption result = " + result);
    }

    public static void tryAlg(CryptAlg alg, String message){
        String cipher = alg.encrypt(message);
        String result = alg.decrypt(cipher);
        System.out.println("Message = " + message);
        System.out.println("Cipher text  = " + cipher);
        System.out.println("decryption result = " + result);
    }
}
