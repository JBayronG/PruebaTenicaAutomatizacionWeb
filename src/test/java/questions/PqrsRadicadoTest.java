package questions;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThrows;

public class PqrsRadicadoTest {
    @Test
    public void extractsRadicadoWithLabel() {
        assertThat(
                PqrsRadicado.extractRadicado("Su número de radicado es: PQRS-12345"),
                equalTo("PQRS-12345")
        );
    }

    @Test
    public void extractsRadicadoWithoutOptionalLabel() {
        assertThat(
                PqrsRadicado.extractRadicado("Radicado # ABC-987"),
                equalTo("ABC-987")
        );
    }

    @Test
    public void acceptsSuccessfulSubmissionConfirmation() {
        assertThat(
                PqrsRadicado.extractRadicado("Tu solicitud ha sido enviada con éxito"),
                equalTo("SUCCESS")
        );
    }

    @Test
    public void rejectsConfirmationWithoutRadicado() {
        assertThrows(
                AssertionError.class,
                () -> PqrsRadicado.extractRadicado("La solicitud fue recibida")
        );
    }
}
