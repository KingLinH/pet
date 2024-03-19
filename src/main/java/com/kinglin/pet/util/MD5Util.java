package com.kinglin.pet.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author huangjl
 * @description MD5加密
 * @since 2024-01-08 16:42
 */
public class MD5Util {

    private static final String SALT = "Ctg4Zw5TuVQ6U9w48tFI8wkvJB3g8ny2";

    public static String encode(String password, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = SALT;
        }
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Add password bytes to digest
            md.update(salt.getBytes());
            // Get the hash's bytes
            byte[] bytes = md.digest(password.getBytes());
            // These bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            // Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }


    public static String getSalt(int len) {
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890").toCharArray();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char saltChar = chars[new Random().nextInt(chars.length)];
            salt.append(saltChar);
        }
        return salt.toString();
    }

}
