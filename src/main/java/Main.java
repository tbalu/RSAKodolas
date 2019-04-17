import java.math.BigInteger;

public class Main {

    public static void main(String [] args){
        //System.out.println(new BigInteger(String.valueOf(12)));
        RSAKodolas rsaKodolas = new RSAKodolas();
       /* BigInteger[] er = rsaKodolas.kibovitettEukledesziAlgoritmus(new BigInteger("2479"),new BigInteger("552"));
        for(BigInteger i: er){
            System.out.println(i);
        }*/
        /*
        BigInteger a = new BigInteger("3");
        BigInteger b = new BigInteger("4");
        System.out.println(a.divide(b));*/

        BigInteger m = rsaKodolas.gyorsHatvanyokzas(new BigInteger("12"),new BigInteger("34"),new BigInteger("56"));
        System.out.println("Megoldas:");
        System.out.println(m);
        BigInteger b = new BigInteger("117");
        /*
        System.out.println(b.bitCount());
        System.out.println(b.toString(2));
        System.out.println(b.bitCount());
    */}
}
