import java.math.BigInteger;

public class Main {

    public static void main(String [] args){
        RSAKodolas rsaKodolas = new RSAKodolas();
        BigInteger[] er = rsaKodolas.kibovitettEukledesziAlgoritmus(new BigInteger("102"),new BigInteger("101"));
        for(BigInteger i: er){
            System.out.println(i);
        }
        /*
        BigInteger a = new BigInteger("3");
        BigInteger b = new BigInteger("4");
        System.out.println(a.divide(b));*/
    }
}
