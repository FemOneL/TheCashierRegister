package com.epam.cashierregister.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordHashierService {

    public static String hash(String message, int key) {
        StringBuilder sb = new StringBuilder();
        byte[] salt = getSalt(key);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            if (md != null) {
                md.update(salt);
            }
            byte[] bytes = md.digest(message.getBytes(StandardCharsets.UTF_8));
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static byte[] getSalt(int k) {
        byte[] salt = new byte[]{61, 24, 24, 43, 32, 22, 13, 13, 13, 44, 13, 123, 127, 5, 32, 33};
        for (byte i = 0; i < salt.length; i++) {
            salt[i] *= k;
        }
        return salt;
    }
}
