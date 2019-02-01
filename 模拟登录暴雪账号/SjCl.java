package hstest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.util.Random;

/**
 * Stanford Javascript Crypto Library (SJCL)
 * sjcl�е�bitArray����java�е�int[]��ÿ��bit(int)ռ4���ֽ�(byte)��
 * bitArrayʮ�����Ƶ�ת����ֻ��ת����byte[]��Ȼ����commons-codec��Hex�Ϳ����ˡ�
 */
public class SjCl {


    public static int[] randomWords(int length) {
        Random random = new Random();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }


    public static String hexFromBits(int[] array) {
        return Hex.encodeHexString(toBytes(array));
    }

    public static byte[] toBytes(int[] array){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            for(int a : array){
                dataOutputStream.writeInt(a);
            }
            return byteArrayOutputStream.toByteArray();
        }catch (IOException e){
            throw new Error(e);     //this shouldn't happen.
        }
    }
    public static int[] toBits(byte[] bytes){
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            int[] array = new int[bytes.length / 4];
            for(int i=0;i<array.length;i++){
                array[i] = dataInputStream.readInt();
            }
            return array;
        }catch (IOException e){
            throw new Error(e);     //this shouldn't happen.
        }
    }

    public static int[] hexToBits(String str){
        try {
            byte[] bytes = Hex.decodeHex(str.toCharArray());
            return toBits(bytes);
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }


}
