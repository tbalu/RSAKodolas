import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class RSA
{
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int        bitlength = 1024;
    private Random     r;

    public RSA()
    {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);
        System.out.println("e: "+e);
        /*while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);*/
        d = kibovitettEukledesziAlgoritmus(phi,e);
        String testString = "a";
        byte[] titkositott = titkosit(testString.getBytes());
        byte[] feltort = feltor(titkositott);
        System.out.println("Osszehasonlitas: " + testString  + " " +  new String(feltort) );
        if(testString.equals( new String(feltort))){
            System.out.println("helyes szam");
        }
            else
        {
            System.out.println("helytelen");
            d = phi.subtract(d);
        }
        //BigInteger d2 = phi.subtract(d);
    }
    public BigInteger fun(BigInteger r,BigInteger phi){
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
        return d;
    }
    public RSA(BigInteger e, BigInteger d, BigInteger N)
    {
        this.e = e;
        this.d = d;
        this.N = N;
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException {


        while (true) {
            RSA rsa = new RSA();


            DataInputStream in = new DataInputStream(System.in);
            String teststring;
            System.out.println("Írd be a szöveget:");
            teststring = in.readLine();
            System.out.println("Titkosítom a szöveget: " + teststring);
            System.out.println("A szöveg bájtokban: "
                    + bytesToString(teststring.getBytes()));
            // titkosit
            byte[] titkositott = rsa.titkosit(teststring.getBytes());
            // feltor
            byte[] feltort = rsa.feltor(titkositott);
            System.out.println("Feltöröm ezeket a bájtokat: " + bytesToString(feltort));
            System.out.println("Feltört szöveg: " + new String(feltort));
        }
    }
    private static String bytesToString(byte[] titkositott)
    {
        String test = "";
        for (byte b : titkositott)
        {
            test += Byte.toString(b);
        }
        return test;
    }

    // Encrypt message
    public byte[] titkosit(byte[] message)
    {
        System.out.println("Titkosít");
        System.out.println("---------------------------------------------------------------------");
        //System.out.println("alap: " +(new BigInteger(message))+"\nkitevo: "+ e+"\nmodulus: "+N);
       // return (new BigInteger(message)).modPow(e, N).toByteArray();
        return gyorsHatvanyokzas((new BigInteger(message)),e,N).toByteArray();

    }

    // Feltori az uzenetet
    public byte[] feltor(byte[] message)
    {
        System.out.println("Feltör");
        System.out.println("---------------------------------------------------------------------");
        //System.out.println("alap: " +(new BigInteger(message))+"\nkitevo: "+ d+"\nmodulus: "+N);
        //return (new BigInteger(message)).modPow(d, N).toByteArray();
        return gyorsHatvanyokzas((new BigInteger(message)),d,N).toByteArray();
    }

    public BigInteger kibovitettEukledesziAlgoritmus(BigInteger rk, BigInteger rkp1) {
        BigInteger egy = new BigInteger("1");
        BigInteger rmod = rk.mod(rkp1);
        BigInteger qkp1 = rk.subtract(rmod).divide(rkp1);
        BigInteger xk = new BigInteger("1");
        BigInteger xkp1 = new BigInteger("0");
        BigInteger yk = new BigInteger("0");
        BigInteger ykp1 = new BigInteger("1");
        //System.out.println("rkp1: " + rkp1 + "qkp1: " + qkp1 + "xkp1: " + xkp1 + "ykp1: " + ykp1 + "rk: " + rk + "xk: " + xk + "yk: " + yk);
        int szamlalo = 1;
        return eukledeszSeged(rkp1, qkp1, xkp1, ykp1, rk, xk, yk, szamlalo,rkp1);

    }


    private BigInteger eukledeszSeged(BigInteger rkp1, BigInteger qkp1, BigInteger xkp1, BigInteger ykp1,
                                        BigInteger rk, BigInteger xk, BigInteger yk, int szamlalo,BigInteger MOD) {
        BigInteger ru = rk.mod(rkp1);
        BigInteger qu = rkp1.divide(ru);
        BigInteger xu = qkp1.multiply(xkp1).add(xk);
        BigInteger yu = qkp1.multiply(ykp1).add(yk);
        //System.out.println("ru: " + ru + "qu: " + qu + "xu: " + xu + "yu: " + yu);
        szamlalo++;
        if (ru.equals(new BigInteger("1")) == true) {
            int kitevo = (int) Math.pow(-1, szamlalo);
            BigInteger[] eredmeny = {qu, xu.multiply(new BigInteger(String.valueOf(kitevo))),
                    yu.multiply(new BigInteger(String.valueOf(kitevo * -1)))};
            //return yu.multiply(new BigInteger(String.valueOf(kitevo * -1)));
            //return yu;
            if(yu.compareTo(BigInteger.ZERO)==1){
                return yu;
            }
            else return MOD.add(yu);
        } else return eukledeszSeged(ru, qu, xu, yu, rkp1, xkp1, ykp1, szamlalo,MOD);

    }

    public BigInteger gyorsHatvanyokzas(BigInteger alap, BigInteger kitevo, BigInteger mod) {
        //System.out.println("alap: "+ alap);
        //System.out.println("kitevo: "+ kitevo);
       // System.out.println("mod: "+ mod);
        String binarisRep = kitevo.toString(2);
        BigInteger hatvModolus = alap;
        BigInteger modulus = BigInteger.ONE;
        //System.out.println("indulas:\n" +binarisRep);
        while (binarisRep != null) {

//            alap.multiply(alap).mod(kitevo);
            if (binarisRep.charAt(binarisRep.length() - 1) == '1') {
                modulus = modulus.multiply(hatvModolus);
                modulus = modulus.mod(mod);
               // System.out.println("reszeredmeny: "+ modulus);
            }
            if (binarisRep.length() != 1) {
                binarisRep = binarisRep.substring(0, binarisRep.length() - 1);
            }
            else{
                binarisRep = null;
            }
            hatvModolus =hatvModolus.pow(2).mod(mod);
           /* System.out.println(binarisRep);
            System.out.println("hatvModulus: "+ hatvModolus);*/
        }
        return modulus;
    }

}
