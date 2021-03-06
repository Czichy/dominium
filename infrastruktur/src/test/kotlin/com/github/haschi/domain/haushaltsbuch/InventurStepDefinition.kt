package com.github.haschi.domain.haushaltsbuch

import com.github.haschi.domain.haushaltsbuch.testing.DieWelt
import com.github.haschi.domain.haushaltsbuch.testing.Inventarposition
import com.github.haschi.domain.haushaltsbuch.testing.MoneyConverter
import com.github.haschi.domain.haushaltsbuch.testing.VermögenswertParameter
import com.github.haschi.domain.haushaltsbuch.testing.schulden
import com.github.haschi.domain.haushaltsbuch.testing.vermögenswerte
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeendeInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Reinvermögen
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schuld
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schulden
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import cucumber.api.DataTable
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Und
import cucumber.api.java.de.Wenn
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import org.assertj.core.api.Assertions.assertThat
import java.util.concurrent.CompletableFuture

class InventurStepDefinition(
        private val welt: DieWelt,
        private val domäne: Anwendungskonfiguration)
{

    private inline fun <T, R> sync(receiver: T, block: T.() -> CompletableFuture<R>): R
    {
        return receiver.block().get()
    }

    @Wenn("^ich die Inventur beginne$")
    fun `wenn ich die Inventur beginne`()
    {
        welt.aktuelleInventur = sync(welt.inventur) {
            send(BeginneInventur(Aggregatkennung.neu()))
        }
    }

    @Dann("^wird mein Inventar leer sein$")
    fun wirdMeinInventarLeerSein()
    {

        val inventar = welt.query.query.send(LeseInventar(welt.aktuelleInventur),
                Inventar::class.java)
        assertThat(inventar).isCompletedWithValueMatching{
            it == Inventar.leer
        }
        .isDone
    }

    @Angenommen("^ich habe mit der Inventur begonnen$")
    fun ichHabeMitDerInventurBegonnen()
    {
        `wenn ich die Inventur beginne`()
    }

    @Dann("^werde ich folgendes Umlaufvermögen in meinem Inventar gelistet haben:$")
    fun `dann werde ich folgende Vermögenswerte in meinem Inventar gelistet haben`(
            vermögenswerte: List<VermögenswertParameter>)
    {

        assertThat(welt.query.query.send(LeseInventar(welt.aktuelleInventur), Inventar::class.java))
                .isCompletedWithValueMatching {
                    it.umlaufvermoegen == Vermoegenswerte(vermögenswerte.map {
                        Vermoegenswert(it.position, it.währungsbetrag)
                    })
                }
                .isDone
    }

    @Dann("^werde ich folgendes Anlagevermögen in meinem Inventar gelistet haben:$")
    fun `dann werde ich folgendes Anlagevermögen in meinem Inventar gelistet haben`(
            vermögenswerte: List<VermögenswertParameter>)
    {
        assertThat(welt.query.query.send(LeseInventar(welt.aktuelleInventur), Inventar::class.java))
                .isCompletedWithValueMatching {
                    it.anlagevermoegen == Vermoegenswerte(vermögenswerte.map {
                        Vermoegenswert(it.position, it.währungsbetrag)
                    })
                }
    }

    @Dann("^werde ich folgende Schulden in meinem Inventar gelistet haben:$")
    fun `dann werde ich folgende Schulden in meinem Inventar gelistet Haben`(
            schulden: List<SchuldParameter>)
    {
        assertThat(welt.query.query.send(LeseInventar(welt.aktuelleInventur), Inventar::class.java))
                .isCompletedWithValueMatching {
                    it.schulden == Schulden(schulden.map { Schuld(it.position, it.währungsbetrag) })
                }
    }

    class SchuldParameter(
            val position: String,

            @XStreamConverter(MoneyConverter::class)
            val währungsbetrag: Währungsbetrag)

    @Wenn("^ich folgendes Inventar erfasse:$")
    fun ichFolgendesInventarErfasse(zeilen: List<Inventarposition>)
    {
        val inventar = Inventar(
                umlaufvermoegen = zeilen.vermögenswerte("Umlaufvermögen"),
                anlagevermoegen = zeilen.vermögenswerte("Anlagevermögen"),
                schulden = zeilen.schulden("Langfristige Schulden"))

        sync(welt.inventur) {
            send(ErfasseInventar(
                    id = welt.aktuelleInventur,
                    inventar = inventar))
        }
    }


    @Und("^ich folgendes Inventar erfassen will:$")
    fun ichFolgendesInventarErfassenWill(zeilen: List<Inventarposition>)
    {
        val inventar = Inventar(
                umlaufvermoegen = zeilen.vermögenswerte("Umlaufvermögen"),
                anlagevermoegen = zeilen.vermögenswerte("Anlagevermögen"),
                schulden = zeilen.schulden("Langfristige Schulden"))

        welt.intention = welt.inventur.send(ErfasseInventar(
                    id = welt.aktuelleInventur,
                    inventar = inventar))
    }

    @Dann("^werde ich folgendes Reinvermögen ermittelt haben:$")
    fun `dann werde ich folgendes Eigenkapital ermittelt haben`(reinvermögen: DataTable)
    {
        val map = reinvermögen.asMap(String::class.java, String::class.java)

        val erwartungswert = Reinvermögen(
                summeDerSchulden = Währungsbetrag.währungsbetrag(map["Summe der Schulden"]!!),
                summeDesVermögens = Währungsbetrag.währungsbetrag(map["Summe des Vermögens"]!!))

        val inventar = welt.query.query.send(
                LeseInventar(welt.aktuelleInventur),
                Inventar::class.java).get()

        assertThat(inventar.reinvermoegen).isEqualTo(erwartungswert)
    }

    @Wenn("^ich die Inventur beenden will$")
    fun ichDieInventurBeendenWill()
    {
        welt.intention =
                welt.inventur.send(BeendeInventur(von = welt.aktuelleInventur))
    }

    @Dann("^werde ich die Fehlermeldung \"([^\"]*)\" erhalten$")
    fun werdeIchDieFehlermeldungErhalten(fehlermeldung: String)
    {
        assert(welt.intention != null) {
            "Es wurde kein Schritt ausgeführt, der eine Intention ausdrückt."
        }

        welt.intention?.let { itention ->
            assertThat(itention).isCompletedExceptionally.withFailMessage("X")
        }
    }

    @Und("^ich habe folgendes Inventar erfasst:$")
    fun ichHabeFolgendesInventarErfasst(zeilen: List<Inventarposition>)
    {
        ichFolgendesInventarErfasse(zeilen)
    }

    @Wenn("^ich die Inventur beende$")
    fun ichDieInventurBeende()
    {
        sync(welt.inventur) {
            send(BeendeInventur(welt.aktuelleInventur))
        }
    }
}
