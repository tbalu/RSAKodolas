import java.math.BigInteger;


public class RSAKodolas {
    public BigInteger[] kibovitettEukledesziAlgoritmus(BigInteger rk,BigInteger rkp1){
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
        System.out.println("rkp1: "+rkp1+"qkp1: "+qkp1+ "xkp1: "+xkp1+"ykp1: "+ykp1+"rk: "+rk+ "xk: "+ xk+"yk: "+yk);
        return eukledeszSeged(rkp1,qkp1,xkp1,ykp1,rk,xk,yk);

    }
    /*private BigInteger[] eukledeszSeged(BigInteger xk, BigInteger xkp1, BigInteger yk,
                                        BigInteger ykp1, BigInteger rk, BigInteger rkp1, BigInteger qkp1){*/
    private BigInteger[] eukledeszSeged(BigInteger rkp1, BigInteger qkp1, BigInteger xkp1, BigInteger ykp1,
                                        BigInteger rk, BigInteger xk, BigInteger yk){
       BigInteger ru = rk.mod(rkp1);
       /*BigInteger qu = rk.subtract(ru);
                    qu.divide(rkp1);*/
       BigInteger qu = rkp1.divide(ru);
       BigInteger xu = qkp1.multiply(xkp1).add(xk);
       BigInteger yu = qkp1.multiply(ykp1).add(yk);
       System.out.println("ru: "+ru+"qu: "+qu+"xu: "+xu+"yu: "+yu);
        if(ru.equals(new BigInteger("1"))==true){
           BigInteger[] eredmeny = {qu,xu,yu};
            return eredmeny;
        }
        else return eukledeszSeged(ru,qu,xu,yu,rkp1,xkp1,ykp1);

    }
}
