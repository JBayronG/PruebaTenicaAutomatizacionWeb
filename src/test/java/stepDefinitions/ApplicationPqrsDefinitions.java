package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Scroll;
import utils.Utility;

import java.util.List;
import java.util.Map;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import questions.PqrsRadicado;
import tasks.pqrs.OpenPqrsPage;
import tasks.pqrs.SubmitPqrsRequest;
import models.PqrsTestData;
import userInterfaces.PqrsHome;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class ApplicationPqrsDefinitions {
    private PqrsTestData configuredTestData;
    @Before
    public void setStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el usuario esta en la pagina de PQRS")
    public void elUsuarioEstaEnLaPaginaDePQRS() {
        List<Map<String, String>> rows = Utility.readExcel("/excel/DatosPruebaWeb.xlsx", "Datos PQRS");
        if (rows == null || rows.isEmpty()) {
            throw new IllegalStateException("No hay datos PQRS en la hoja Datos PQRS");
        }
        configuredTestData = PqrsTestData.fromMap(rows.get(0));
        theActorCalled("Cliente").wasAbleTo(OpenPqrsPage.open());
    }

    @When("ingresa a la opcion crear solicitud PQRS")
    public void ingresaALaOpcionCrearSolicitudPQRS() {
        OnStage.theActorInTheSpotlight().attemptsTo(Scroll.to(PqrsHome.PUNTO_VENTA));
    }

    @And("diligencia el formulario con los datos requeridos")
    public void diligenciaElFormularioConLosDatosRequeridos() {
        OnStage.theActorInTheSpotlight().attemptsTo(SubmitPqrsRequest.withData(configuredTestData));
    }

    @Then("el sistema genera un mensaje de confirmacion con el numero de radicado")
    public void elSistemaGeneraUnMensajeDeConfirmacionConElNumeroDeRadicado() {
        OnStage.theActorInTheSpotlight().should(
                seeThat(PqrsRadicado.fromConfirmation(), not(isEmptyOrNullString()))
        );
    }
}
