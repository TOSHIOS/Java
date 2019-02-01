package hstest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;

/**
 * ģ��SrpClientSession��
 */
public class SrpClientSession {


    private BigInteger modulus;
    private BigInteger generator;
    private BigInteger sessionKeyS;

    private Random random = new Random();

    public SrpClientSession(String t,String i){
        this.modulus = new BigInteger(t,16);
        this.generator = new BigInteger(i,16);
    }


    public BigInteger[] step1(String t,String i,String e,String r){
        BigInteger h = new BigInteger(r,16);
        if(!isValidPublicValue(modulus,h)){
            throw new RuntimeException("Invalid public B value");
        }
        BigInteger s = generatePrivateValue();
        BigInteger o = computePublicClientValueA(modulus,generator,s);
        BigInteger c = computeU(modulus,o,h);
        if(!isValidUValue(c)){
            throw new RuntimeException("Invalid u value");
        }
        BigInteger a = computeK(modulus,generator);
        BigInteger u = computeX(t,i,e);
        this.sessionKeyS = this.computeSessionKeyS(this.modulus, this.generator, a, u, c, s, h);
        BigInteger l = this.computeClientEvidenceM1(o, h, this.sessionKeyS);
        return new BigInteger[]{o,l};
    }

    private BigInteger computeClientEvidenceM1(BigInteger t, BigInteger i, BigInteger e) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");;
            md.update(t.toByteArray());
            md.update(i.toByteArray());
            md.update(e.toByteArray());
            byte[] bytes = md.digest();
            return new BigInteger(Hex.encodeHexString(bytes),16);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


    private BigInteger computePublicClientValueA(BigInteger t,BigInteger i,BigInteger e){
        return i.modPow(e, t);
    }

    private BigInteger computeSessionKeyS(BigInteger t,BigInteger i,BigInteger e,BigInteger r,BigInteger n,BigInteger s,BigInteger o){
        BigInteger h = n.multiply(r).add(s);
        BigInteger c = i.modPow(r, t).multiply(e);
        return o.subtract(c).modPow(h, t);
    }

    private BigInteger generatePrivateValue(){
        String t = SjCl.hexFromBits(SjCl.randomWords(32));
        return new BigInteger(t,16);
    }

    private boolean isValidPublicValue(BigInteger t,BigInteger i){
        return !i.mod(t).equals(BigInteger.ZERO);
    }

    private boolean isValidUValue(BigInteger t){
        return !t.equals(BigInteger.ZERO);
    }

    private BigInteger computeU(BigInteger t,BigInteger  i,BigInteger  e){
        return this.hashPaddedPair(t, i.toString(16), e.toString(16));
    }

    private BigInteger computeK(BigInteger t,BigInteger i){
        return this.hashPaddedPair(t, t.toString(16), i.toString(16));
    }

    private BigInteger computeX(String t,String e,String r){
        String n = hash(t + ":" + e.toUpperCase(),false);
        String s = hash(r + n,true);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<s.length();i+=2){
            stringBuilder.append(s.charAt(s.length() - i - 2));
            stringBuilder.append(s.charAt(s.length() - i - 1));
        }
        return new BigInteger(stringBuilder.toString(),16);
    }

    private BigInteger hashPaddedPair(BigInteger t, String i, String e) {
        int r = 2 * (4 * t.toString(16).length() + 7 >> 3);
        String n = pad(r,i) + pad(r,e);
        return new BigInteger(this.hash(n, true), 16);
    }

    private String hash(String t,boolean i){
        byte [] bytes = i ?  SjCl.toBytes(SjCl.hexToBits(t)) : t.getBytes();
        String e = Hex.encodeHexString(DigestUtils.sha256(bytes));
        return pad(64,e);
    }

    //���ַ���i���Ȳ��뵽tλ��ʵ����js��ͬ������һ����
    private String pad(int t,String i){
        if(t == i.length()){
            return i;
        }else if(t < i.length()){
            return i.substring(i.length() - t);
        }else{
            StringBuilder stringBuilder = new StringBuilder(i.length() + t);
            for(int v=0;v<t - i.length();v++){
                stringBuilder.append('0');
            }
            stringBuilder.append(i);
            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) {
//        SrpClientSession srpClientSession = new SrpClientSession("86a7f6deeb306ce519770fe37d556f29944132554ded0bd68205e27f3231fef5a10108238a3150c59caf7b0b6478691c13a6acf5e1b5adafd4a943d4a21a142b800e8a55f8bfbac700eb77a7235ee5a609e350ea9fc19f10d921c2fa832e4461b7125d38d254a0be873dfc27858acb3f8b9f258461e4373bc3a6c2a9634324ab","2");
//        System.out.println(Arrays.toString(srpClientSession.step1("75193D6E89A747601E38FFDA0D6D3FFAF4A8B681FC8A02AB4A57A9260FBAB208","123456","8D9FA1D66FC50BDEABE7C1F99EAA3E940120FB6479263609F7D604D69DB862D7","cf3730eab9284031fc26a0416e1c6d1d24dfa8745875454a61a70aed533f76b8e1cfba5b17ccd18a73cbf18112f904bf4a5910746099d026452d8d2d9b159403210bc7caab5ecdbed955cb70eddfd2382c90c19dc528cd84bda70e609772090b41a72c448f9071ef25368798289fabb24d23c7513f6289a2050ae065994f14d")));
//        SrpClientSession srpClientSession = new SrpClientSession("86a7f6deeb306ce519770fe37d556f29944132554ded0bd68205e27f3231fef5a10108238a3150c59caf7b0b6478691c13a6acf5e1b5adafd4a943d4a21a142b800e8a55f8bfbac700eb77a7235ee5a609e350ea9fc19f10d921c2fa832e4461b7125d38d254a0be873dfc27858acb3f8b9f258461e4373bc3a6c2a9634324ab","2");
//        System.out.println(Arrays.toString(srpClientSession.step1("E985FFD0EEA96A0697D5DC6E41AAE37B71B9B8164169ADC43A2F4581EF41EF98","wc2456123009","B9E2517CDEAF34AC30568605E2E2496017E2B090622F9DCEE81F4C91B8C4AB6C","4aaf75aa03a5fc2e1b6c163287568461a898295587e1d0907703271f5f7ea942af329e27927a151a28ebecf74233e0699bd7434397027f8690b7de1f9ffea5a71bd444dbe80ddc3039cb9b4ed8294303c9dee34bd8a3858aa3a10c715fa3244a53420281f99e452f13e47fcf48aaab71d8037b9a1e5743ce9b3f511a65f8adf6")));
        SrpClientSession srpClientSession = new SrpClientSession("86a7f6deeb306ce519770fe37d556f29944132554ded0bd68205e27f3231fef5a10108238a3150c59caf7b0b6478691c13a6acf5e1b5adafd4a943d4a21a142b800e8a55f8bfbac700eb77a7235ee5a609e350ea9fc19f10d921c2fa832e4461b7125d38d254a0be873dfc27858acb3f8b9f258461e4373bc3a6c2a9634324ab","2");
    	System.out.println("1");
        BigInteger[] bigIntegers = srpClientSession.step1("E985FFD0EEA96A0697D5DC6E41AAE37B71B9B8164169ADC43A2F4581EF41EF98","wc2456123009","B9E2517CDEAF34AC30568605E2E2496017E2B090622F9DCEE81F4C91B8C4AB6C","4aaf75aa03a5fc2e1b6c163287568461a898295587e1d0907703271f5f7ea942af329e27927a151a28ebecf74233e0699bd7434397027f8690b7de1f9ffea5a71bd444dbe80ddc3039cb9b4ed8294303c9dee34bd8a3858aa3a10c715fa3244a53420281f99e452f13e47fcf48aaab71d8037b9a1e5743ce9b3f511a65f8adf6");
        System.out.println(bigIntegers[0].toString(16));
        System.out.println(bigIntegers[1].toString(16));
    }

}
