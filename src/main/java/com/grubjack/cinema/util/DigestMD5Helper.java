package com.grubjack.cinema.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * {@code DigestMD5Helper} utility class
 * <p>
 * Provides methods for MD5 crypt algorithm
 */
public class DigestMD5Helper {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(DigestMD5Helper.class);
    /**
     * Algorithm for hashing
     */
    private static final String ALGORITHM = "MD5";

    /**
     * Compute hash string of given plain text
     *
     * @param plainText input string
     * @return MD5 hash of input string
     */
    public static String computeHash(String plainText) {
        try {
            byte[] hexBinary = MessageDigest.getInstance(ALGORITHM).digest(plainText.getBytes());
            return String.format("%032x", new BigInteger(1, hexBinary));
        } catch (NoSuchAlgorithmException e) {
            log.error("Unsupported algorithm for digest password");
        }
        return "";
    }
}
