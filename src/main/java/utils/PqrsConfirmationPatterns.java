package utils;

import java.util.regex.Pattern;

public final class PqrsConfirmationPatterns {
    public static final String SUCCESS_RESULT = "SUCCESS";
    public static final Pattern RADICADO_PATTERN = Pattern.compile(
            "(?iu)(?:n[uú]mero\\s+de\\s+)?radicado\\s*(?:es\\s*)?[:#-]?\\s*([a-z0-9-]+)"
    );
    public static final Pattern SUCCESS_PATTERN = Pattern.compile(
            "(?iu)tu\\s+solicitud\\s+ha\\s+sido\\s+enviada\\s+con\\s+[eé]xito"
    );

    private PqrsConfirmationPatterns() {
    }
}
