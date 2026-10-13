package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import utils.GlobalWait;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static userInterfaces.BillingAddressPage.BILLING_SECTION;
import static userInterfaces.BillingAddressPage.DATA_LINK;
import static userInterfaces.BillingAddressPage.EDIT_BILLING_ADDRESS;
import static userInterfaces.BillingAddressPage.ADDRESS_MODAL;

public class OpenBillingAddressEditor implements Task {
    public static OpenBillingAddressEditor open() {
        return Tasks.instrumented(OpenBillingAddressEditor.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                GlobalWait.untilVisible(DATA_LINK),
                Click.on(DATA_LINK),
                GlobalWait.untilVisible(BILLING_SECTION),
                Scroll.to(BILLING_SECTION),
                GlobalWait.untilVisible(EDIT_BILLING_ADDRESS),
                Click.on(EDIT_BILLING_ADDRESS),
                GlobalWait.untilVisible(ADDRESS_MODAL)
        );
    }
}
