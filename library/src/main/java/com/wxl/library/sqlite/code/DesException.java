package com.wxl.library.sqlite.code;

/**
 * Des加密、解密异常
 *
 * @author wangxiulong
 */
public class DesException extends Exception {
    private static final long serialVersionUID = 1L;

    public DesException() {
        super();
    }

    public DesException(String message, Throwable cause) {
        super(message, cause);
    }

    public DesException(String message) {
        super(message);
    }

    public DesException(Throwable cause) {
        super(cause);
    }
}