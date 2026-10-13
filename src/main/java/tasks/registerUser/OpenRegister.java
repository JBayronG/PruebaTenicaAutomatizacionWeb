package tasks.registerUser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import static userInterfaces.CreateAccountHome.REGISTER_TOGGLE;
import static userInterfaces.CreateAccountHome.REGISTER_USER;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;
import utils.GlobalWait;

public class OpenRegister implements Task {

        @Override
        public <T extends Actor> void performAs(T actor) {
            EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
            String baseUrl = net.serenitybdd.core.environment.EnvironmentSpecificConfiguration
                    .from(environmentVariables).getProperty("webdriver.base.url");
            String accountUrl = baseUrl.endsWith("/") ? baseUrl + "mi-cuenta/" : baseUrl + "/mi-cuenta/";
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            driver.navigate().to(accountUrl);

            actor.attemptsTo(
                    GlobalWait.untilVisible(REGISTER_TOGGLE),
                    net.serenitybdd.screenplay.actions.Click.on(REGISTER_TOGGLE),
                    GlobalWait.untilVisible(REGISTER_USER)
            );

        }

        public static OpenRegister openRegister() {
            return Tasks.instrumented(OpenRegister.class);
        }
    }
