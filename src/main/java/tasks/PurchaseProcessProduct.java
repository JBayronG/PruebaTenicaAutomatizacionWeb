package tasks;

import interactions.ScrollToCenter;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

import static userInterfaces.ProductHome.*;
import static utils.GlobalWait.untilVisible;

public class PurchaseProcessProduct implements Task {

    public static PurchaseProcessProduct purchaseProcessProduct() {
        return Tasks.instrumented(PurchaseProcessProduct.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                untilVisible(BOTON_COMPRAR_AHORA_URL),
                Click.on(BOTON_COMPRAR_AHORA_URL),
                untilVisible(BOTON_FINALIZAR_COMPRA),
                ScrollToCenter.of(BOTON_FINALIZAR_COMPRA),
                Click.on(BOTON_FINALIZAR_COMPRA),
                untilVisible(BOTON_CONTINUAR),
                ScrollToCenter.of(BOTON_CONTINUAR),
                Click.on(BOTON_CONTINUAR),
                untilVisible(BOTON_REALIZAR_PEDIDO),
                untilVisible(CHECKBOX_TERMINOS),
                Click.on(CHECKBOX_TERMINOS),
                ScrollToCenter.of(BOTON_REALIZAR_PEDIDO),
                Click.on(BOTON_REALIZAR_PEDIDO),
                untilVisible(NUMERO_PEDIDO_FALLBACK)
        );
    }
}
