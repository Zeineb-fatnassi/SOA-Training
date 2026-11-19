package rest.utilities;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public final class JwtKey {

    private static final String SECRET =
            "atelier-rest-5-esprit-cle-secrete-assez-longue-pour-hs512-2025-2026";

    private static final Key KEY = new SecretKeySpec(
            SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA512");

    private JwtKey() {
    }

    public static Key get() {
        return KEY;
    }
}
