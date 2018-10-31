package com.todaysoft.lims.system.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by manbu on 15/9/25.
 */
public class AuthenticateUtil {

    private final static int salt_length = 8;
    private final static String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()";

    private final static int invite_code_length = 6;
    private final static String chars_ic = "23456789abcdefghijkmnpqrstuvwxyz";

    //生成邀请码（去除了0 o 1 l）
    public static String generateInviteCode(){

        return RandomStringUtils.random(invite_code_length, chars_ic);

    }

    /**
     * 生成盐
     * */
    public static String generateSalt() {

        return RandomStringUtils.random(salt_length, chars);
    }

    public static String decodeFromBase64(String input) {

        byte[] data = Base64.decodeBase64(input);

        return new String(data);
    }

    public static String salt(String origin, String salt) {

        return DigestUtils.sha256Hex(DigestUtils.md5Hex(origin) + salt);
    }

    public static boolean validate(String base64Input, String salt, String encodePwd) {

        return StringUtils.equals(salt(base64Input, salt), encodePwd);
    }

}
