package com.wxl.library.sqlite.code;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

class Des {
    private static final String MODE = "DES";

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws DesException
     */
    public static String encrypt(String data, String key) throws DesException {
        try {
            DESKeySpec desKey = new DESKeySpec(key.getBytes("GBK"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MODE);
            SecretKey securekey = keyFactory.generateSecret(desKey);

            SecureRandom random = new SecureRandom();
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);

            byte[] in = data.getBytes();
            byte[] des = cipher.doFinal(in);
            byte[] out = Base64.encode(des);
            return new String(out);
        } catch (Exception e) {
            throw new DesException(e.getMessage());
        }
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws DesException
     */
    public static String decrypt(String data, String key) throws DesException {
        try {
            DESKeySpec desKey = new DESKeySpec(key.getBytes("GBK"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MODE);
            SecretKey securekey = keyFactory.generateSecret(desKey);

            SecureRandom random = new SecureRandom();
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);

            byte[] in = data.getBytes();
            byte[] des = Base64.decode(in);
            byte[] out = cipher.doFinal(des);
            return new String(out);
        } catch (Exception e) {
            throw new DesException(e.getMessage());
        }
    }
}
