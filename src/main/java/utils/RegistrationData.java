package utils;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public record RegistrationData(String id, String firstName, String lastName, String email, String password) {
    public static RegistrationData fromTestData(Map<String, String> testData) {
        if (testData == null) {
            throw new IllegalArgumentException("Registration test data is required");
        }
        return new RegistrationData(
                String.valueOf(ThreadLocalRandom.current().nextLong(1_000_000_000L, 9_999_999_999L)),
                required(testData, "Nombres"),
                required(testData, "Apellidos"),
                required(testData, "Correo electronico"),
                required(testData, "Contraseña")
        );
    }

    private static String required(Map<String, String> data, String key) {
        String value = data.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing registration data: " + key);
        }
        return value.trim();
    }
}
