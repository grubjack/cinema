package com.grubjack.cinema.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Urban Aleksandr
 */
public class DigestMD5Helper {
    private static Logger log = LoggerFactory.getLogger(DigestMD5Helper.class);
    private static final String ALGORITHM = "MD5";

    public static String computeHash(String plainText) {
        try {
            byte[] hexBinary = MessageDigest.getInstance(ALGORITHM).digest(plainText.getBytes());
            return String.format("%032x", new BigInteger(1, hexBinary));
        } catch (NoSuchAlgorithmException e) {
            log.error("Unsupported algorithm for digest password");
        }
        return null;
    }
}
