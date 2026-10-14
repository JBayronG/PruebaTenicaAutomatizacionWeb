package tasks;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

import java.util.Map;

import static userInterfaces.ProductHome.PRODUCTO_LINK;
import static userInterfaces.ProductHome.TALLA;
import static utils.GlobalWait.untilVisible;

public class SelectProduct implements Task {

    public static SelectProduct selectProduct() {
        return Tasks.instrumented(SelectProduct.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Map<String, String> datosFila = Serenity.sessionVariableCalled("datoFila");
        String producto = datosFila.get("Nombre Producto");
        String talla = datosFila.get("Talla");

        actor.attemptsTo(
                untilVisible(PRODUCTO_LINK.of(producto)),
                Click.on(PRODUCTO_LINK.of(producto))
        );

        if (talla != null && !talla.trim().isBlank()) {
            actor.attemptsTo(
                    untilVisible(TALLA.of(talla)),
                    Click.on(TALLA.of(talla))
            );
        }
    }
}
