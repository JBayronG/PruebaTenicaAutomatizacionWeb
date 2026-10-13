package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import userInterfaces.CreateAccountHome;

public class SuccessfulRegistration implements Question<Boolean> {
    public static SuccessfulRegistration isConfirmed() {
        return new SuccessfulRegistration();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            boolean registrationError = CreateAccountHome.REGISTRATION_ERRORS.resolveAllFor(actor)
                    .stream().anyMatch(org.openqa.selenium.WebElement::isDisplayed);
            boolean accountPageVisible = CreateAccountHome.ACCOUNT_CONTENT.resolveFor(actor).isDisplayed();
            boolean loggedIn = CreateAccountHome.ACCOUNT_LOGOUT.resolveFor(actor).isDisplayed();
            return !registrationError && accountPageVisible && loggedIn;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
