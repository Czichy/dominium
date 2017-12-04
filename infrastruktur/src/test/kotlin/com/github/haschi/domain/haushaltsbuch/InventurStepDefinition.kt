package com.github.haschi.domain.haushaltsbuch

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseSchulden
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseUmlaufvermögen
import com.github.haschi.domain.haushaltsbuch.modell.core.events.BeendeInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.queries.LeseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Inventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.InventurAusnahme
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Reinvermögen
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Schuld
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Schulden
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswert
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswerte
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import com.github.haschi.domain.haushaltsbuch.testing.Anweisungskonfiguration
import com.github.haschi.domain.haushaltsbuch.testing.MoneyConverter
import com.github.haschi.domain.haushaltsbuch.testing.VermögenswertParameter
import cucumber.api.DataTable
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Und
import cucumber.api.java.de.Wenn
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable

class InventurStepDefinition(
        private val welt: DieWelt,
        private val anweisung: Anweisungskonfiguration)
{

    @Wenn("^ich die Inventur beginne$")
    fun wenn_ich_die_inventur_beginne()
    {
        welt.aktuelleInventur = Aggregatkennung.neu()
            anweisung.commandGateway.sendAndWait<Any>(
                    BeginneInventur(welt.aktuelleInventur!!))
    }

    @Dann("^wird mein Inventar leer sein$")
    fun wirdMeinInventarLeerSein()
    {
        val future = anweisung.queryGateway.send(LeseInventar(welt.aktuelleInventur!!), Inventar::class.java)
        val single = Single.fromFuture(future)
        val inventar = single.blockingGet()


//        val inventar = abfrage.commandGateway.sendAndWait<Inventar>(
//                LeseInventar(welt.aktuelleInventur!!))

        assertThat(inventar).isEqualTo(Inventar.leer)
    }

    @Angenommen("^ich habe mit der Inventur begonnen$")
    fun ichHabeMitDerInventurBegonnen()
    {
        wenn_ich_die_inventur_beginne()
    }

    @Wenn("^ich mein Umlaufvermögen \"([^\"]*)\" in Höhe von \"([^\"]*)\" erfasse$")
    fun ichMeinUmlaufvermögenInHöheVonErfasse(
            position: String,
            währungsbetrag: Währungsbetrag)
    {
        anweisung.commandGateway.sendAndWait<Any>(
                ErfasseUmlaufvermögen(
                        inventurkennung = welt.aktuelleInventur!!,
                        position = position,
                        währungsbetrag = währungsbetrag))
    }

    @Dann("^werde ich folgendes Umlaufvermögen in meinem Inventar gelistet haben:$")
    fun werdeIchFolgendeVermögenswerteInMeinemInventarGelistetHaben(
            vermögenswerte: List<VermögenswertParameter>)
    {

        val inventar = anweisung.queryGateway.send(
                LeseInventar(welt.aktuelleInventur!!),
                Inventar::class.java).get()

        assertThat(inventar.umlaufvermoegen)
                .isEqualTo(Vermoegenswerte(vermögenswerte.map {
                    Vermoegenswert(it.position, it.währungsbetrag)
                }))
    }

    @Dann("^werde ich folgendes Anlagevermögen in meinem Inventar gelistet haben:$")
    fun werdeIchFolgendesAnlagevermögenInMeinemInventarGelistetHaben(
            vermögenswerte: List<VermögenswertParameter>)
    {
        val inventar = anweisung.queryGateway.send(
                LeseInventar(welt.aktuelleInventur!!),
                Inventar::class.java).get()

        assertThat(inventar.anlagevermoegen)
                .isEqualTo(Vermoegenswerte(vermögenswerte.map {
                    Vermoegenswert(it.position, it.währungsbetrag)
                }))
    }

    @Wenn("^ich meine Schulden \"([^\"]*)\" in Höhe von \"([^\"]*)\" erfasse$")
    fun ichMeineSchuldenInHöheVonErfasse(
            position: String,
            währungsbetrag: Währungsbetrag)
    {

        anweisung.commandGateway.sendAndWait<Any>(
                ErfasseSchulden(
                        inventurkennung = welt.aktuelleInventur!!,
                        position = position,
                        währungsbetrag = währungsbetrag))
    }

    @Dann("^werde ich folgende Schulden in meinem Inventar gelistet haben:$")
    fun werdeIchFolgendeSchuldenInMeinemInventarGelistetHaben(
            schulden: List<SchuldParameter>)
    {
        val inventar = anweisung.queryGateway.send(
                LeseInventar(welt.aktuelleInventur!!),
                Inventar::class.java).get()

        assertThat(inventar.schulden)
                .isEqualTo(Schulden(schulden.map { Schuld(it.position, it.währungsbetrag) }));
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

        anweisung.commandGateway.sendAndWait<Any>(
                ErfasseInventar(
                        id = welt.aktuelleInventur!!,
                        inventar = inventar))
    }


    @Und("^ich folgendes Inventar erfassen will:$")
    fun ichFolgendesInventarErfassenWill(zeilen: List<Inventarposition>)
    {
        welt.intention = { ichFolgendesInventarErfasse(zeilen) }
    }

    @Dann("^werde ich folgendes Reinvermögen ermittelt haben:$")
    fun werdeIchFolgendesEigenkapitalErmitteltHaben(reinvermögen: DataTable)
    {
        val map = reinvermögen.asMap(String::class.java, String::class.java)

        val erwartungswert = Reinvermögen(
                summeDerSchulden = Währungsbetrag.währungsbetrag(map["Summe der Schulden"]!!),
                summeDesVermögens = Währungsbetrag.währungsbetrag(map["Summe des Vermögens"]!!))

        val inventar = anweisung.queryGateway.send(
                LeseInventar(welt.aktuelleInventur!!),
                Inventar::class.java).get()

        assertThat(inventar.reinvermoegen).isEqualTo(erwartungswert)
    }

    @Wenn("^ich die Inventur beenden will$")
    fun ichDieInventurBeendenWill()
    {
        welt.intention = {
            anweisung.commandGateway.sendAndWait<Unit?>(
                    BeendeInventur(von = welt.aktuelleInventur!!))
        }
    }

    @Dann("^werde ich die Fehlermeldung \"([^\"]*)\" erhalten$")
    fun werdeIchDieFehlermeldungErhalten(fehlermeldung: String)
    {
        assert(welt.intention != null) {
            "Es wurde kein Schritt ausgeführt, der eine Intention ausdrückt."
        }

        assertThat(catchThrowable(welt.intention))
                .hasCause(InventurAusnahme(fehlermeldung))
    }

    @Und("^ich habe folgendes Inventar erfasst:$")
    fun ichHabeFolgendesInventarErfasst(zeilen: List<Inventarposition>)
    {
        ichFolgendesInventarErfasse(zeilen)
    }

    @Wenn("^ich die Inventur beende$")
    fun ichDieInventurBeende()
    {
        anweisung.commandGateway.sendAndWait<Any>(
                BeendeInventur(welt.aktuelleInventur!!))
    }
}
