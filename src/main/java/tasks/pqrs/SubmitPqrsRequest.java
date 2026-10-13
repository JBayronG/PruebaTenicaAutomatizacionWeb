package tasks.pqrs;

import interactions.ClickJs;
import interactions.Pause;
import interactions.ScrollToCenter;
import models.PqrsTestData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import userInterfaces.PqrsHome;

public class SubmitPqrsRequest implements Task {
    private final PqrsTestData requestData;

    private SubmitPqrsRequest(PqrsTestData requestData) {
        this.requestData = requestData;
    }

    public static SubmitPqrsRequest withData(PqrsTestData requestData) {
        return new SubmitPqrsRequest(requestData);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                FillPqrsForm.with(requestData),
                ScrollToCenter.of(PqrsHome.ENVIAR),
                Pause.beforeSubmit(),
                ClickJs.en(PqrsHome.ENVIAR)
        );
        PqrsFormResponse.await(BrowseTheWeb.as(actor).getDriver());
    }
}
