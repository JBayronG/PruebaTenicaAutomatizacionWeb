package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import questions.PqrsRadicado;
import tasks.pqrs.OpenPqrsPage;
import tasks.pqrs.SubmitPqrsRequest;
import models.PqrsTestData;
import userInterfaces.PqrsHome;
import utils.Utility;

import java.util.List;
import java.util.Map;
import java.nio.file.Path;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class ApplicationPqrsDefinitions {
    private PqrsTestData configuredTestData;
    @Before
    public void setStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el usuario carga la informacion PQRS desde el Excel {string}")
    public void elUsuarioCargaLaInformacionPqrsDesdeElExcel(String fila, io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String rutaExcel = data.get(0).get("rutaExcel");
        String hoja = data.get(0).get("hoja");
        int numeroFila = Integer.parseInt(fila);
        String resourcePath = rutaExcel.startsWith("src/test/resources")
                ? rutaExcel.substring("src/test/resources".length())
                : rutaExcel;
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }
        List<Map<String, String>> rows = utils.Utility.readExcel(resourcePath, hoja);
        if (rows == null || numeroFila < 1 || numeroFila > rows.size()) {
            throw new IllegalStateException("La fila PQRS especificada no existe en el archivo Excel");
        }
        Map<String, String> pqrsRow = rows.get(numeroFila - 1);
        List<Map<String, String>> credentials = utils.Utility.readExcel(resourcePath, "Set Datos PQRS");
        if (credentials == null || numeroFila > credentials.size()) {
            throw new IllegalStateException("La fila de credenciales PQRS especificada no existe en el archivo Excel");
        }
        Map<String, String> testData = new java.util.HashMap<>(credentials.get(numeroFila - 1));
        testData.putAll(pqrsRow);
        configuredTestData = PqrsTestData.fromMap(testData);
        Serenity.setSessionVariable("pqrsRowNumber").to(numeroFila);
        Serenity.setSessionVariable("pqrsTestData").to(configuredTestData);
        Serenity.setSessionVariable("datoFila").to(testData);
    }

    @And("el usuario PQRS abre la pagina de bonBonite")
    public void elUsuarioPqrsAbreLaPaginaDeBonBonite() {
        OnStage.theActorCalled("Cliente").wasAbleTo(tasks.browser.OpenBrowser.openBrowser());
    }

    @And("el usuario PQRS inicia sesion")
    public void elUsuarioPqrsIniciaSesion() {
        OnStage.theActorInTheSpotlight().attemptsTo(tasks.LoginBonBonite.loginBonBonite());
    }

    @When("ingresa a la opcion crear solicitud PQRS")
    public void ingresaALaOpcionCrearSolicitudPQRS() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                OpenPqrsPage.open(),
                Scroll.to(PqrsHome.PUNTO_VENTA)
        );
    }

    @And("diligencia el formulario con los datos requeridos")
    public void diligenciaElFormularioConLosDatosRequeridos() {
        OnStage.theActorInTheSpotlight().attemptsTo(SubmitPqrsRequest.withData(configuredTestData));
    }

    @Then("el sistema genera un mensaje de confirmacion con el numero de radicado")
    public void elSistemaGeneraUnMensajeDeConfirmacionConElNumeroDeRadicado() {
        String radicado = OnStage.theActorInTheSpotlight().asksFor(PqrsRadicado.fromConfirmation());
        Integer fila = Serenity.sessionVariableCalled("pqrsRowNumber");
        Utility.saveExcelValue(
                Path.of("src/test/resources/excel/DatosPruebaWeb.xlsx").toString(),
                "Datos PQRS",
                fila,
                "Numero de radicado",
                radicado
        );
        org.hamcrest.MatcherAssert.assertThat(radicado, not(isEmptyOrNullString()));
    }
}
