package utils;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public final class LoginSupport {
    private static final Duration WAIT_TIMEOUT = GlobalWait.TIMEOUT;

    private LoginSupport() {
    }

    public static String accountUrl() {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        String baseUrl = EnvironmentSpecificConfiguration.from(variables)
                .getProperty("webdriver.base.url");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("The webdriver.base.url property must be configured");
        }
        return baseUrl.endsWith("/") ? baseUrl + "mi-cuenta/" : baseUrl + "/mi-cuenta/";
    }

    public static String credential(Map<String, String> testData, String column, String environmentVariable) {
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

    public static <T extends Actor> boolean isVisible(T actor, Target target) {
        return target.resolveAllFor(actor).stream()
                .anyMatch(org.openqa.selenium.WebElement::isDisplayed);
    }

    public static <T extends Actor> void waitFor(T actor, WebDriver driver, Target target, String message) {
        new WebDriverWait(driver, WAIT_TIMEOUT)
                .withMessage(message)
                .until(currentDriver -> isVisible(actor, target));
    }

}
