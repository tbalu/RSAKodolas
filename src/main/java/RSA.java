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
        System.out.println("Új RSA inicializálása:");
        r = new Random();

        p = this.veletlenPrimetGeneral();
        System.out.println("p: " + p);

        q = this.veletlenPrimetGeneral();
        System.out.println("q: " + q);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = this.veletlenPrimetGeneral();
        System.out.println("e: "+ e);
        System.out.println("e: "+e);
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

    }

    public RSA(BigInteger e, BigInteger d, BigInteger N)
    {
        this.e = e;
        this.d = d;
        this.N = N;
    }


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
            System.out.println("Titkosit");
            byte[] titkositott = rsa.titkosit(teststring.getBytes());
            System.out.println("Titkosított bajtsorozat");
            for(byte ba: titkositott){
                System.out.print(ba);
            }
            System.out.println("\n");
            System.out.println("Feltor");
            // feltor
            byte[] feltort = rsa.feltor(titkositott);

            System.out.println("Feltört bájtok: " + bytesToString(feltort));
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

        System.out.println("alap: " +(new BigInteger(message))+"\nkitevo: "+ e+"\nmodulus: "+N);
        return gyorsHatvanyozas((new BigInteger(message)),e,N).toByteArray();

    }

    // Feltori az uzenetet
    public byte[] feltor(byte[] message)
    {

        return gyorsHatvanyozas((new BigInteger(message)),d,N).toByteArray();
    }

    public BigInteger kibovitettEukledesziAlgoritmus(BigInteger rk, BigInteger rkp1) {
        BigInteger egy = new BigInteger("1");
        BigInteger rmod = rk.mod(rkp1);
        BigInteger qkp1 = rk.subtract(rmod).divide(rkp1);
        BigInteger xk = new BigInteger("1");
        BigInteger xkp1 = new BigInteger("0");
        BigInteger yk = new BigInteger("0");
        BigInteger ykp1 = new BigInteger("1");
        int szamlalo = 1;
        return eukledeszSeged(rkp1, qkp1, xkp1, ykp1, rk, xk, yk, szamlalo,rkp1);

    }


    private BigInteger eukledeszSeged(BigInteger rkp1, BigInteger qkp1, BigInteger xkp1, BigInteger ykp1,
                                        BigInteger rk, BigInteger xk, BigInteger yk, int szamlalo,BigInteger MOD) {
        BigInteger ru = rk.mod(rkp1);
        BigInteger qu = rkp1.divide(ru);
        BigInteger xu = qkp1.multiply(xkp1).add(xk);
        BigInteger yu = qkp1.multiply(ykp1).add(yk);
        szamlalo++;
        if (ru.equals(new BigInteger("1")) == true) {
            int kitevo = (int) Math.pow(-1, szamlalo);


            if(yu.compareTo(BigInteger.ZERO)==1){
                return yu;
            }
            else return MOD.add(yu);
        } else return eukledeszSeged(ru, qu, xu, yu, rkp1, xkp1, ykp1, szamlalo,MOD);

    }

    public BigInteger gyorsHatvanyozas(BigInteger alap, BigInteger kitevo, BigInteger mod) {
        String binarisRep = kitevo.toString(2);
        BigInteger hatvModolus = alap;
        BigInteger modulus = BigInteger.ONE;

        while (binarisRep != null) {

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

        }
        return modulus;
    }

    public BigInteger veletlenPrimetGeneral(){
        r = new Random();
        BigInteger primJelolt = new BigInteger(bitlength/2,r);
        boolean primetTalaltam=false;
        while(  !primetTalaltam ){
            if(MillerRabinTeszt(primJelolt)){
                if (Fermatteszt(primJelolt)) {
                    return primJelolt;
                }
            }
            primJelolt = new BigInteger(bitlength/2,r);

        }
        System.out.println("primet talaltam");
        return primJelolt;
    }
    private boolean Fermatteszt(BigInteger p){
        BigInteger alap = new BigInteger(p.bitCount(),new Random());
        alap = alap.subtract(BigInteger.ONE);

        if(this.gyorsHatvanyozas(alap,p.subtract(BigInteger.ONE),p).compareTo(BigInteger.ONE) ==0){
            return true;
        }
        return false;
    }
    public boolean MillerRabinTeszt(BigInteger primJelolt){

        BigInteger alap = new BigInteger(primJelolt.bitCount(),new Random());
        alap = alap.subtract(BigInteger.ONE);
        BigInteger pm1 = primJelolt.subtract(BigInteger.ONE);
        BigInteger ketto = new BigInteger("2");
        BigInteger s = new BigInteger("0");
        while(pm1.divide(ketto).mod(ketto)==BigInteger.ZERO){
            pm1 = pm1.divide(ketto);
                s =  s.add(BigInteger.ONE);


        }


        for (; s.compareTo(BigInteger.ZERO)!=-1; s =  s.subtract(BigInteger.ONE)){

            if(gyorsHatvanyozas(alap,ketto.pow(s.intValue()).multiply(pm1),primJelolt).compareTo(BigInteger.ONE)==0){
                return true;
            }
        }
        return false;
    }

}
