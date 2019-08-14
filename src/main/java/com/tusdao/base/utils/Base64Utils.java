package com.tusdao.base.utils;


import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author yueli
 * @date 2019-08-07 12:45
 */
public class Base64Utils {

    final static Base64 base64 = new Base64();

    public static String encodeToStr(String plaintext) {
        byte[] plaintextByes = null;
        try {
            plaintextByes = plaintext.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        String encodeText = base64.encodeToString(plaintextByes);
        return encodeText;

    }
}
