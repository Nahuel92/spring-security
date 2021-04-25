package org.nahuel.rodriguez.springsecuritysecondhandson.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class GenerateCodeUtil {
    public static String generateCode() {
        try {
            final var secureRandom = SecureRandom.getInstanceStrong();
            final var randomNumber = secureRandom.nextInt(9000) + 1000;
            return String.valueOf(randomNumber);
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("Error when generating the random code.");
        }
    }
}
