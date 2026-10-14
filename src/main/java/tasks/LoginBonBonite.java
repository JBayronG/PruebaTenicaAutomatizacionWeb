package tasks;

import interactions.ClickJs;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.WebDriver;

import java.util.Map;
import userInterfaces.LoginBonBonitePage;
import utils.LoginSupport;

public class LoginBonBonite implements Task {
    public static LoginBonBonite loginBonBonite() {
        return Tasks.instrumented(LoginBonBonite.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        driver.navigate().to(LoginSupport.accountUrl());

        if (LoginSupport.isVisible(actor, LoginBonBonitePage.ACCOUNT_LOGOUT)) {
            return;
        }

        LoginSupport.waitFor(actor, driver, LoginBonBonitePage.INPUT_USER,
                "The account login form did not load");

        Map<String, String> testData = Serenity.sessionVariableCalled("datoFila");
        actor.attemptsTo(
                Enter.theValue(LoginSupport.credential(testData, "Usuario", "BONBONITE_TEST_USER"))
                        .into(LoginBonBonitePage.INPUT_USER),
                Enter.theValue(LoginSupport.credential(testData, "Contraseña", "BONBONITE_TEST_PASSWORD"))
                        .into(LoginBonBonitePage.INPUT_PASS),
                ClickJs.en(LoginBonBonitePage.BTTN_LOGIN)
        );

        LoginSupport.waitForAuthenticatedAccount(actor, driver);
    }
}
