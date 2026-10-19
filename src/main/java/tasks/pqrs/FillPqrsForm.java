package tasks.pqrs;

import interactions.ClickJs;
import models.PqrsTestData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import userInterfaces.PqrsHome;

public class FillPqrsForm implements Task {
    private final PqrsTestData data;

    private FillPqrsForm(PqrsTestData data) {
        this.data = data;
    }

    public static FillPqrsForm with(PqrsTestData data) {
        return new FillPqrsForm(data);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SelectFromOptions.byVisibleText(data.puntoVenta()).from(PqrsHome.PUNTO_VENTA),
                Enter.theValue(data.nombreCompleto()).into(PqrsHome.NOMBRE),
                Enter.theValue(data.direccion()).into(PqrsHome.DIRECCION),
                SelectFromOptions.byVisibleText(data.tipoDocumento()).from(PqrsHome.TIPO_DOCUMENTO),
                Enter.theValue(data.numeroDocumento()).into(PqrsHome.NUMERO_DOCUMENTO),
                Enter.theValue(data.telefono()).into(PqrsHome.TELEFONO),
                Enter.theValue(data.correo()).into(PqrsHome.CORREO),
                SelectFromOptions.byVisibleText(data.tipoSolicitud()).from(PqrsHome.TIPO_SOLICITUD),
                Enter.theValue(data.descripcion()).into(PqrsHome.DESCRIPCION)
        );
        fillCausalIfVisible(actor);
        actor.attemptsTo(ClickJs.en(PqrsHome.AUTORIZACION));
    }

    private <T extends Actor> void fillCausalIfVisible(T actor) {
        WebElement field = BrowseTheWeb.as(actor).getDriver().findElement(By.name("select-4"));
        if (!field.isDisplayed()) {
            return;
        }
        if (data.causal().isBlank()) {
            throw new IllegalStateException("BONBONITE_PQRS_CAUSE is required for the selected PQRS request type");
        }
        actor.attemptsTo(SelectFromOptions.byVisibleText(data.causal()).from(PqrsHome.CAUSAL));
    }
}
