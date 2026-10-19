package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import userInterfaces.PqrsHome;
import utils.PqrsConfirmationPatterns;

import java.util.regex.Matcher;

public class PqrsRadicado implements Question<String> {

    public static PqrsRadicado fromConfirmation() {
        return new PqrsRadicado();
    }

    @Override
    public String answeredBy(Actor actor) {
        String confirmation = PqrsHome.CONFIRMACION_ENVIO.resolveFor(actor).getText();
        String radicado = PqrsHome.NUMERO_RADICADO.resolveFor(actor).getText();
        return extractRadicado(confirmation + " " + radicado);
    }

    static String extractRadicado(String confirmationMessage) {
        validateMessage(confirmationMessage);

        Matcher radicadoMatcher = PqrsConfirmationPatterns.RADICADO_PATTERN.matcher(confirmationMessage);
        if (radicadoMatcher.find()) {
            return radicadoMatcher.group(1);
        }
        if (PqrsConfirmationPatterns.SUCCESS_PATTERN.matcher(confirmationMessage).find()) {
            return PqrsConfirmationPatterns.SUCCESS_RESULT;
        }
        throw new AssertionError(
                "The PQRS response did not include a radicado number or success confirmation"
        );
    }

    private static void validateMessage(String confirmationMessage) {
        if (confirmationMessage == null || confirmationMessage.isBlank()) {
            throw new AssertionError("The PQRS confirmation message was empty");
        }
    }
}
