package tasks;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

import java.util.Map;

import static utils.GlobalWait.untilVisible;

import static userInterfaces.ProductHome.*;

public class SelectCategory implements Task {

    private final String opcion;

    public SelectCategory(String opcion) {
        this.opcion = opcion;
    }

    public static SelectCategory selectProduct(String opcion) {
        return Tasks.instrumented(SelectCategory.class, opcion);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        Map<String, String> datosFila = Serenity.sessionVariableCalled("datoFila");

        String categoria = datosFila.get("Nombre Categoria");

        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("La fila de datos no contiene una categoría");
        }

        if (!opcion.equalsIgnoreCase(categoria)) {
            throw new IllegalArgumentException("La categoría del escenario no coincide con el Excel");
        }

        actor.attemptsTo(
                untilVisible(CATEGORIA.of(categoria)),
                Click.on(CATEGORIA.of(categoria))
        );
    }
}