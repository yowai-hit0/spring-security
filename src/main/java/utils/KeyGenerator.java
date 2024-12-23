package utils;
import java.security.SecureRandom;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;

public class KeyGenerator {
    // This is the same key generation logic from your original code
    public static String generateBase64EncodedKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomKey = new byte[32]; // 32 bytes = 256 bits
        secureRandom.nextBytes(randomKey);

        // Return the Base64 encoded key
        return java.util.Base64.getEncoder().encodeToString(randomKey);
    }

    // This method decodes the Base64 encoded key and uses it to create the HMAC signing key
    private Key getSigningKey(String secretKey) {
        // Decode the Base64 encoded key
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        System.out.println("Decoded Key: ");
        for (byte b : keyBytes) {
            System.out.printf("%02x ", b);  // Print the key in hexadecimal for debugging
        }
        System.out.println();

        // Use the decoded key to create the HMAC signing key
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static void main(String[] args) {
        // Generate a Base64 encoded 256-bit key
        String base64Key = generateBase64EncodedKey();
        System.out.println("Generated Base64 Key: " + base64Key);

        // Use the key in getSigningKey method
        KeyGenerator keyGen = new KeyGenerator();
        Key signingKey = keyGen.getSigningKey(base64Key);
        System.out.println("HMAC Signing Key: " + signingKey);
    }
}
