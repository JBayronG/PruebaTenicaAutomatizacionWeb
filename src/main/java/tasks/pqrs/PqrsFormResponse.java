package tasks.pqrs;

import exceptions.pqrs.PqrsSubmissionException;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import userInterfaces.PqrsHome;

import java.time.Duration;

final class PqrsFormResponse {
    private PqrsFormResponse() {
    }

    private static boolean hasConfirmation(Actor actor) {
        return PqrsHome.CONFIRMACION_ENVIO.resolveAllFor(actor).stream()
                .anyMatch(element -> element.isDisplayed())
                || PqrsHome.MENSAJE_RESPUESTA.resolveAllFor(actor).stream()
                .anyMatch(element -> element.isDisplayed() && !element.getText().isBlank());
    }

    static void await(Actor actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(60))
                    .until(currentDriver -> hasConfirmation(actor));
        } catch (RuntimeException exception) {
            throw new PqrsSubmissionException(
                    "No se obtuvo respuesta del formulario PQRS después de enviar la solicitud");
        }

        String response = PqrsHome.MENSAJE_RESPUESTA.resolveAllFor(actor).stream()
                .filter(element -> element.isDisplayed())
                .map(element -> element.getText())
                .filter(text -> !text.isBlank())
                .findFirst()
                .orElseGet(() -> PqrsHome.CONFIRMACION_ENVIO.resolveFor(actor).getText());
        if (response.toLowerCase().contains("recaptcha")
                || response.toLowerCase().contains("captcha")) {
            throw new PqrsSubmissionException("La verificación CAPTCHA de PQRS falló: " + response);
        }
    }
}
