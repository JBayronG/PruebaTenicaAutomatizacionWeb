package models;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

public class PqrsTestDataTest {
    @Test
    public void reportsRequiredEnvironmentVariablesThatAreMissing() {
        List<String> missing = PqrsTestData.missingEnvironmentVariables(java.util.Map.of());

        assertThat(missing, hasItems(
                "BONBONITE_PQRS_STORE",
                "BONBONITE_PQRS_FULL_NAME",
                "BONBONITE_PQRS_ADDRESS",
                "BONBONITE_PQRS_DOCUMENT_TYPE",
                "BONBONITE_PQRS_DOCUMENT_NUMBER",
                "BONBONITE_PQRS_PHONE",
                "BONBONITE_PQRS_EMAIL",
                "BONBONITE_PQRS_REQUEST_TYPE",
                "BONBONITE_PQRS_DESCRIPTION"
        ));
    }

    @Test
    public void doesNotReportTheOptionalCauseAsRequired() {
        assertThat(
                PqrsTestData.missingEnvironmentVariables(java.util.Map.of()),
                not(hasItems("BONBONITE_PQRS_CAUSE"))
        );
    }
}
