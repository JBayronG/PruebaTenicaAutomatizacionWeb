package models;

import java.util.Map;

public record LoginCredentials(String username, String password) {
    public static LoginCredentials fromTestData(Map<String, String> testData) {
        return new LoginCredentials(
                valueFrom(testData, "Usuario", "BONBONITE_TEST_USER"),
                valueFrom(testData, "Contraseña", "BONBONITE_TEST_PASSWORD")
        );
    }

    private static String valueFrom(Map<String, String> testData, String column, String environmentVariable) {
        String value = testData == null ? null : testData.get(column);
        if (value == null || value.isBlank()) {
            value = System.getenv(environmentVariable);
        }
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Configure " + environmentVariable + " with an authorized test account"
            );
        }
        return value.trim();
    }
}
