package tasks.registerUser;

import interactions.ClickJs;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import userInterfaces.CreateAccountHome;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import utils.GlobalWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class CreateAccount implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {

        Map<String, String> datosFila = Serenity.sessionVariableCalled("datoFila");

        String cedula = String.valueOf(ThreadLocalRandom.current()
                .nextLong(1_000_000_000L, 9_999_999_999L));
        String nombre = datosFila.get("Nombres");
        String apellido = datosFila.get("Apellidos");
        String correo = configuredRegistrationEmail(datosFila);
        String pass = registrationPassword(datosFila);
        String confirmaPass = pass;

        actor.attemptsTo(
                Enter.theValue(cedula).into(CreateAccountHome.INPUT_ID),
                Enter.theValue(nombre).into(CreateAccountHome.INPUT_FIRSTNAME),
                Enter.theValue(apellido).into(CreateAccountHome.INPUT_LASTNAME),
                Enter.theValue(correo).into(CreateAccountHome.INPUT_EMAIL),
                Enter.theValue(pass).into(CreateAccountHome.INPUT_PASSWORD),
                Enter.theValue(confirmaPass).into(CreateAccountHome.INPUT_PASSWORD2)
        );

        actor.attemptsTo(
                Click.on(CreateAccountHome.SELECT_AUTORIZATION)
        );

        actor.attemptsTo(
                ClickJs.en(CreateAccountHome.BTTN_REGISTER)
        );

        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        Boolean completed = new WebDriverWait(driver, GlobalWait.TIMEOUT)
                .withMessage("Registration did not complete or show a validation error")
                .until(currentDriver -> {
                    boolean accountPageVisible = CreateAccountHome.ACCOUNT_CONTENT.resolveAllFor(actor)
                            .stream()
                            .anyMatch(org.openqa.selenium.WebElement::isDisplayed);
                    boolean logoutVisible = CreateAccountHome.ACCOUNT_LOGOUT.resolveAllFor(actor)
                            .stream()
                            .anyMatch(org.openqa.selenium.WebElement::isDisplayed);
                    if (accountPageVisible || logoutVisible) {
                        return true;
                    }

                    boolean registrationError = CreateAccountHome.REGISTRATION_ERRORS.resolveAllFor(actor)
                            .stream()
                            .anyMatch(org.openqa.selenium.WebElement::isDisplayed);
                    return registrationError ? false : null;
                });
        if (!completed) {
            throw new IllegalStateException("The site rejected the registration test data");
        }
    }

    public static CreateAccount createAccount() {
        return Tasks.instrumented(CreateAccount.class);
    }

    private String configuredRegistrationEmail(Map<String, String> testData) {
        String configuredEmail = testData == null ? null : testData.get("Correo electronico");
        if (configuredEmail == null || configuredEmail.isBlank()) {
            configuredEmail = System.getenv("BONBONITE_REGISTER_EMAIL");
        }
        if (configuredEmail == null || configuredEmail.isBlank()) {
            throw new IllegalStateException(
                    "Configure Correo electronico in the registration data or BONBONITE_REGISTER_EMAIL"
            );
        }
        int atIndex = configuredEmail.indexOf('@');
        if (atIndex <= 0 || atIndex == configuredEmail.length() - 1) {
            throw new IllegalStateException("The registration test email is invalid");
        }

        return configuredEmail.substring(0, atIndex)
                + "+"
                + UUID.randomUUID().toString().substring(0, 8)
                + configuredEmail.substring(atIndex);
    }

    private String registrationPassword(Map<String, String> testData) {
        String password = testData == null ? null : testData.get("Contraseña");
        if (password == null || password.isBlank()) {
            password = System.getenv("BONBONITE_TEST_PASSWORD");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Configure Contraseña in the registration data or BONBONITE_TEST_PASSWORD"
            );
        }
        return password.trim();
    }

    private String requiredEnvironmentVariable(String variableName) {
        String value = System.getenv(variableName);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Configure " + variableName + " with authorized test data"
            );
        }
        return value.trim();
    }
}
