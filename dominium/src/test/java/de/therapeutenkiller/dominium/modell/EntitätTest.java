package de.therapeutenkiller.dominium.modell;

import com.mscharhag.oleaster.runner.OleasterRunner;
import de.therapeutenkiller.dominium.testdomäne.TestAggregat;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.runner.RunWith;

import java.util.UUID;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(OleasterRunner.class)
public class EntitätTest {

    private Entität<UUID> subjectUnderTest;

    {
        final UUID identitätsmerkmal = UUID.randomUUID();

        beforeEach(() -> this.subjectUnderTest = new TestAggregat(identitätsmerkmal));

        describe("Eine Entität", () -> {

            it("besitzt eine nicht veränderbare Identität", () -> {
                assertThat(this.subjectUnderTest.getIdentitätsmerkmal()).isEqualTo(identitätsmerkmal);
            });

            it("benutzt das Identitätsmerkmal für den Äquivalenz-Vergleich", () -> {
                EqualsVerifier.forClass(TestAggregat.class)
                    .withOnlyTheseFields("identitätsmerkmal")
                    .verify();
            });
        });
    }
}
