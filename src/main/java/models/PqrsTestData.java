package models;

import java.util.ArrayList;
import java.util.List;

public record PqrsTestData(
        String puntoVenta,
        String nombreCompleto,
        String direccion,
        String tipoDocumento,
        String numeroDocumento,
        String telefono,
        String correo,
        String tipoSolicitud,
        String descripcion,
        String causal
) {
    public static PqrsTestData fromEnvironment() {
        return fromMap(System.getenv());
    }

    public static PqrsTestData fromMap(java.util.Map<String, String> values) {
        List<String> missing = new ArrayList<>();
        String puntoVenta = required(values, "BONBONITE_PQRS_STORE", "puntoVenta", missing);
        String nombreCompleto = required(values, "BONBONITE_PQRS_FULL_NAME", "nombreCompleto", missing);
        String direccion = required(values, "BONBONITE_PQRS_ADDRESS", "direccion", missing);
        String tipoDocumento = required(values, "BONBONITE_PQRS_DOCUMENT_TYPE", "tipoDocumento", missing);
        String numeroDocumento = required(values, "BONBONITE_PQRS_DOCUMENT_NUMBER", "numeroDocumento", missing);
        String telefono = required(values, "BONBONITE_PQRS_PHONE", "telefono", missing);
        String correo = required(values, "BONBONITE_PQRS_EMAIL", "correo", missing);
        String tipoSolicitud = required(values, "BONBONITE_PQRS_REQUEST_TYPE", "tipoSolicitud", missing);
        String descripcion = required(values, "BONBONITE_PQRS_DESCRIPTION", "descripcion", missing);
        String causal = firstValue(values, "BONBONITE_PQRS_CAUSE", "causal");

        if (!missing.isEmpty()) {
            throw new IllegalStateException(
                    "Missing required PQRS test data: " + String.join(", ", missing)
            );
        }

        return new PqrsTestData(puntoVenta, nombreCompleto, direccion, tipoDocumento,
                numeroDocumento, telefono, correo, tipoSolicitud, descripcion,
                causal == null ? "" : causal.trim());
    }

    private static String required(java.util.Map<String, String> values, String environmentName,
                                   String excelName, List<String> missing) {
        String value = firstValue(values, environmentName, excelName);
        if (value == null || value.isBlank()) {
            missing.add(excelName);
            return "";
        }
        return value.trim();
    }

    private static String firstValue(java.util.Map<String, String> values, String first, String second) {
        String value = values.get(first);
        return value == null ? values.get(second) : value;
    }

    public static List<String> missingEnvironmentVariables() {
        return missingEnvironmentVariables(System.getenv());
    }

    static List<String> missingEnvironmentVariables(java.util.Map<String, String> environment) {
        List<String> missing = new ArrayList<>();
        for (String variableName : List.of(
                "BONBONITE_PQRS_STORE",
                "BONBONITE_PQRS_FULL_NAME",
                "BONBONITE_PQRS_ADDRESS",
                "BONBONITE_PQRS_DOCUMENT_TYPE",
                "BONBONITE_PQRS_DOCUMENT_NUMBER",
                "BONBONITE_PQRS_PHONE",
                "BONBONITE_PQRS_EMAIL",
                "BONBONITE_PQRS_REQUEST_TYPE",
                "BONBONITE_PQRS_DESCRIPTION"
        )) {
            String value = environment.get(variableName);
            if (value == null || value.isBlank()) {
                missing.add(variableName);
            }
        }
        return List.copyOf(missing);
    }

    private static String required(String variableName, List<String> missing) {
        String value = System.getenv(variableName);
        if (value == null || value.isBlank()) {
            missing.add(variableName);
            return "";
        }
        return value.trim();
    }
}
