package questions;

import models.Orden;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import userInterfaces.ProductHome;

public class ObtenerResumenOrden implements Question<Orden> {
    public static ObtenerResumenOrden deLaCompra() {
        return new ObtenerResumenOrden();
    }

    @Override
    public Orden answeredBy(Actor actor) {
        String numero = firstVisibleText(actor, ProductHome.NUMERO_PEDIDO, ProductHome.NUMERO_PEDIDO_FALLBACK);
        if (numero.isEmpty()) {
            throw new AssertionError("No se confirmó la orden después de completar la compra");
        }
        return new Orden(
                numero,
                firstVisibleText(actor, ProductHome.FECHA_PEDIDO, ProductHome.FECHA_PEDIDO_FALLBACK),
                firstVisibleText(actor, ProductHome.TOTAL_PEDIDO, ProductHome.TOTAL_PEDIDO_FALLBACK),
                firstVisibleText(actor, ProductHome.METODO_PAGO, ProductHome.METODO_PAGO_FALLBACK)
        );
    }

    private String firstVisibleText(Actor actor, Target primary, Target fallback) {
        String value = visibleText(primary, actor);
        return value.isEmpty() ? visibleText(fallback, actor) : value;
    }

    private String visibleText(Target target, Actor actor) {
        return target.resolveAllFor(actor).stream()
                .filter(org.openqa.selenium.WebElement::isDisplayed)
                .map(org.openqa.selenium.WebElement::getText)
                .map(String::trim)
                .filter(java.util.function.Predicate.not(String::isEmpty))
                .findFirst()
                .orElse("");
    }
}
