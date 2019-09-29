package com.plc.abcdefg.gateway.common.util;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Md5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(16);
        stringBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = stringBuilder.length();
        if(len < 16){
            for(int i = 0; i < 16 - len; i++){
                stringBuilder.append("0");
            }
        }
        /**
         * 摘要内容：密码与"加盐"值后按规则掺入48为的字符之后形成的一个字符串
         */
        String Salt = stringBuilder.toString();
        charSequence = md5Hex(charSequence + Salt);
        char[] cs = new char[48];
        for(int i = 0; i < 48; i += 3){
            cs[i] = charSequence.charAt(i / 3 * 2);
            char c = Salt.charAt(i / 3);
            cs[i + 1]  = c;
            cs[i + 2] = charSequence.charAt(i / 3 * 2 + 1);
        }

        return String.valueOf(cs);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for(int i = 0 ; i  < 48; i += 3){
            cs1[i / 3 * 2] = s.charAt(i);
            cs1[i / 3 * 2 + 1 ] = s.charAt(i + 2);
            cs2[i / 3] = s.charAt(i + 1);
        }
        String Salt = new String(cs2);

        return md5Hex(charSequence + Salt) .equals(String.valueOf(cs1));
    }

    private static String md5Hex(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return new String(new Hex().encode(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }
}
