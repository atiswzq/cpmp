package cn.cofco.cpmp.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by Xujy on 2017/5/26.
 * for [加密解密算法工具类] in cpmp
 */
public final class CryptUtils {

    public static final String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }


    private static final int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }


    /**
     * md5加密
     * @param s
     * @return
     */
    public final static String md5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * base64加密
     * @param str
     * @param salt
     * @return
     */
    public static final String encrypt(String str, String salt) throws UnsupportedEncodingException {

        String ori = encryptText(str);

        return encryptText(ori + salt);
    }

    /**
     * base64解密
     * @param str
     * @param salt
     * @return
     */
    public static final String decrypt(String str, String salt) throws IOException {

        String encrypted = decryptText(str);
        String encrypt = encrypted.substring(0, encrypted.length() - salt.length());
        return decryptText(encrypt);
    }


    private static final String encryptText(String ori) throws UnsupportedEncodingException {
        byte[] bytVal = ori.getBytes("UTF-8");
        byte[] encrypt = encryptDigitShift(bytVal);
        return new BASE64Encoder().encode(encrypt);
    }

    private static final String decryptText(String ori) throws IOException {
        byte[] bytVal = new BASE64Decoder().decodeBuffer(ori);
        byte[] decrypt = decryptDigitShift(bytVal);
        return new String(decrypt, "UTF-8");
    }

    private static final byte[] encryptDigitShift(byte[] saltBytes) {
        byte[] encryptedBytes = new byte[saltBytes.length];
        for (int i = 0; i < saltBytes.length; i++) {
            String asc = String.valueOf(saltBytes[i]);
            if (Integer.parseInt(asc) % 2 == 1) {
                encryptedBytes[i] = (byte) (Integer.parseInt(asc) - 1);
            } else {
                encryptedBytes[i] = (byte) (Integer.parseInt(asc) + 1);
            }
        }

        return  encryptedBytes;
    }

    private static final byte[] decryptDigitShift(byte[] saltBytes) {
        byte[] encryptedBytes = new byte[saltBytes.length];

        for (int i = 0; i < saltBytes.length; i++) {
            String asc = String.valueOf(saltBytes[i]);
            if (Integer.parseInt(asc) % 2 == 1) {
                encryptedBytes[i] = (byte) (Integer.parseInt(asc) - 1);
            } else {
                encryptedBytes[i] = (byte) (Integer.parseInt(asc) + 1);
            }
        }

        return  encryptedBytes;
    }

}
