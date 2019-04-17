import java.math.BigInteger;


public class RSAKodolas {
    public BigInteger[] kibovitettEukledesziAlgoritmus(BigInteger rk, BigInteger rkp1) {
       /* BigInteger r3 = new BigInteger("0");
        BigInteger egy = new BigInteger("1");
        int szamlalo=0;
        BigInteger x1 = new BigInteger("1");
        BigInteger x2 = new BigInteger("0");
        BigInteger y1 = new BigInteger("0");
        BigInteger y2 = new BigInteger("1");
        while (r3.equals(egy)==false ) {
            r3 = r1.mod(r2);
            szamlalo++;
        }*/

        BigInteger egy = new BigInteger("1");
        BigInteger rmod = rk.mod(rkp1);
        BigInteger qkp1 = rk.subtract(rmod).divide(rkp1);
        BigInteger xk = new BigInteger("1");
        BigInteger xkp1 = new BigInteger("0");
        BigInteger yk = new BigInteger("0");
        BigInteger ykp1 = new BigInteger("1");
        System.out.println("rkp1: " + rkp1 + "qkp1: " + qkp1 + "xkp1: " + xkp1 + "ykp1: " + ykp1 + "rk: " + rk + "xk: " + xk + "yk: " + yk);
        int szamlalo = 1;
        return eukledeszSeged(rkp1, qkp1, xkp1, ykp1, rk, xk, yk, szamlalo);

    }

    /*private BigInteger[] eukledeszSeged(BigInteger xk, BigInteger xkp1, BigInteger yk,
                                        BigInteger ykp1, BigInteger rk, BigInteger rkp1, BigInteger qkp1){*/
    private BigInteger[] eukledeszSeged(BigInteger rkp1, BigInteger qkp1, BigInteger xkp1, BigInteger ykp1,
                                        BigInteger rk, BigInteger xk, BigInteger yk, int szamlalo) {
        BigInteger ru = rk.mod(rkp1);
       /*BigInteger qu = rk.subtract(ru);
                    qu.divide(rkp1);*/
        BigInteger qu = rkp1.divide(ru);
        BigInteger xu = qkp1.multiply(xkp1).add(xk);
        BigInteger yu = qkp1.multiply(ykp1).add(yk);
        System.out.println("ru: " + ru + "qu: " + qu + "xu: " + xu + "yu: " + yu);
        szamlalo++;
        if (ru.equals(new BigInteger("1")) == true) {
            int kitevo = (int) Math.pow(-1, szamlalo);
            BigInteger[] eredmeny = {qu, xu.multiply(new BigInteger(String.valueOf(kitevo))),
                    yu.multiply(new BigInteger(String.valueOf(kitevo * -1)))};
            return eredmeny;
        } else return eukledeszSeged(ru, qu, xu, yu, rkp1, xkp1, ykp1, szamlalo);

    }

    public BigInteger gyorsHatvanyokzas(BigInteger alap, BigInteger kitevo, BigInteger mod) {
        String binarisRep = kitevo.toString(2);
        BigInteger hatvModolus = alap;
        BigInteger modulus = BigInteger.ONE;
        System.out.println("indulas:\n" +binarisRep);
        while (binarisRep != null) {

            alap.multiply(alap).mod(kitevo);
            if (binarisRep.charAt(binarisRep.length() - 1) == '1') {
                modulus = modulus.multiply(hatvModolus);
                modulus = modulus.mod(mod);
                System.out.println("reszeredmeny: "+ modulus);
            }
            if (binarisRep.length() != 1) {
                binarisRep = binarisRep.substring(0, binarisRep.length() - 1);
            }
            else{
                binarisRep = null;
            }
            hatvModolus =hatvModolus.pow(2).mod(mod);
            System.out.println(binarisRep);
            System.out.println("hatvModulus: "+ hatvModolus);
        }
        return modulus;
    }

}