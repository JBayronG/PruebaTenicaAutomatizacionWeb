package tasks;

import interactions.ClickJs;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utility;

import utils.GlobalWait;

import static userInterfaces.ProductHome.*;

public class PurchaseProcessProduct implements Task {

    public static PurchaseProcessProduct purchaseProcessProduct() {
        return Tasks.instrumented(PurchaseProcessProduct.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(ClickJs.en(BOTON_COMPRAR_AHORA_URL));
        new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), GlobalWait.TIMEOUT)
                .until(driver -> !BOTON_FINALIZAR_COMPRA.resolveAllFor(actor).isEmpty());
        Utility.ScrollTo(actor,BOTON_FINALIZAR_COMPRA);
        actor.attemptsTo(ClickJs.en(BOTON_FINALIZAR_COMPRA));
        Utility.ScrollTo(actor,BOTON_CONTINUAR);
        actor.attemptsTo(ClickJs.en(BOTON_CONTINUAR));
        new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), GlobalWait.TIMEOUT)
                .until(driver -> !BOTON_REALIZAR_PEDIDO.resolveAllFor(actor).isEmpty());
        actor.attemptsTo(ClickJs.en(CHECKBOX_TERMINOS));
        WebElement orderButton = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), GlobalWait.TIMEOUT)
                .until(driver -> BOTON_REALIZAR_PEDIDO.resolveFor(actor).isDisplayed()
                        ? BOTON_REALIZAR_PEDIDO.resolveFor(actor) : null);
        ((org.openqa.selenium.JavascriptExecutor) BrowseTheWeb.as(actor).getDriver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", orderButton);
        actor.attemptsTo(ClickJs.en(BOTON_REALIZAR_PEDIDO));
        new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), GlobalWait.TIMEOUT)
                .until(driver -> driver.getCurrentUrl().contains("order-received")
                        || !NUMERO_PEDIDO.resolveAllFor(actor).isEmpty()
                        || !NUMERO_PEDIDO_FALLBACK.resolveAllFor(actor).isEmpty());
    }
}
