package tasks.pqrs;

import exceptions.pqrs.PqrsSubmissionException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

final class PqrsFormResponse {
    private static final By RESPONSE_MESSAGE = By.cssSelector(
            "#forminator-module-1000452 .forminator-response-message");

    private PqrsFormResponse() {
    }

    static void await(WebDriver driver) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(60)).until(PqrsFormResponse::hasVisibleMessage);
        } catch (RuntimeException exception) {
            throw new PqrsSubmissionException(
                    "No se obtuvo respuesta del formulario PQRS después de enviar la solicitud");
        }

        String response = visibleMessage(driver);
        if (response.toLowerCase().contains("recaptcha")
                || response.toLowerCase().contains("captcha")) {
            throw new PqrsSubmissionException("La verificación CAPTCHA de PQRS falló: " + response);
        }
    }

    private static boolean hasVisibleMessage(WebDriver driver) {
        return !visibleMessage(driver).isBlank();
    }

    private static String visibleMessage(WebDriver driver) {
        return driver.findElements(RESPONSE_MESSAGE).stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .filter(java.util.function.Predicate.not(String::isBlank))
                .findFirst()
                .orElse("");
    }
}
