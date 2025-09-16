package tg.groupedeux.rescinema.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtil {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256; // bits

    private PasswordUtil() {}

    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(char[] password, String saltBase64) {
        byte[] salt = Base64.getDecoder().decode(saltBase64);
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Erreur de hachage mot de passe", e);
        } finally {
            spec.clearPassword();
        }
    }

    public static boolean verifyPassword(char[] password, String saltBase64, String expectedHashBase64) {
        String actual = hashPassword(password, saltBase64);
        return constantTimeEquals(actual, expectedHashBase64);
    }

    private static boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        byte[] x = a.getBytes();
        byte[] y = b.getBytes();
        if (x.length != y.length) return false;
        int res = 0;
        for (int i = 0; i < x.length; i++) {
            res |= x[i] ^ y[i];
        }
        return res == 0;
    }
}
