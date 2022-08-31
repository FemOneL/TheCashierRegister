package com.epam.cashierregister.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class used for hashing password by SHA-512
 */
public class PasswordHashingService {
    static Logger LOG = LogManager.getLogger(PasswordHashingService.class);

    /**
     * @param password needed to hashing
     * @param key user id for generating salt
     * @return hashing password
     */
    public static String hash(String password, int key) {
        LOG.info("Start hashing password");
        StringBuilder sb = new StringBuilder();
        byte[] salt = getSalt(key);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            if (md != null) {
                md.update(salt);
            }
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e);
        }
        return sb.toString();
    }

    /**
     * @param key for generating salt
     * @return salt in <code>byte[]</code>
     */
    private static byte[] getSalt(int key) {
        LOG.info("Getting salt");
        byte[] salt = new byte[]{61, 24, 24, 43, 32, 22, 13, 13, 13, 44, 13, 123, 127, 5, 32, 33};
        for (byte i = 0; i < salt.length; i++) {
            salt[i] *= key;
        }
        return salt;
    }
}
