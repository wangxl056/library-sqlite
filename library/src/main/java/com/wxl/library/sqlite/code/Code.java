package com.wxl.library.sqlite.code;

public final class Code {
    public static final class base64 {
        public static byte[] encode(byte[] data) {
            return Base64.encode(data);
        }

        public static byte[] decode(byte[] data) {
            return Base64.decode(data);
        }
    }

    public static final class des {
        public static String encrypt(String data, String key) throws DesException {
            return Des.encrypt(data, key);
        }

        public static String decrypt(String data, String key) throws DesException {
            return Des.decrypt(data, key);
        }
    }
}
