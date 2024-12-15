package edu.ou.flowerstore.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MACGenerator {
    public static String generateMAC(String data, String key) {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            byte[] hmacBytes = mac.doFinal(data.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hmacBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return null;
        }
    }
}
