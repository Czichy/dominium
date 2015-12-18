package de.therapeutenkiller.haushaltsbuch.domaene

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import spock.lang.Specification

class HaushaltsbuchTest extends Specification {

    def "Hinzufügen eines nicht extistierenden Kontos"() {

        given: "Angenommen ein neues Haushaltsbuch wurde angelegt"
        def haushaltsbuch = new Haushaltsbuch();

        when: "Wenn ich versuche eine nicht existierendes Konto hinzuzufügen"
        haushaltsbuch.neuesKontoHinzufügen(null)

        then: "Dann wird eine NullPointerException ausgelöst."
        thrown ArgumentIstNullException
    }

    def "Konto suchen"() {

        given: def haushaltsbuch = new Haushaltsbuch();
        // haushaltsbuch.neuesKontoHinzufügen(konto, fünfEuro);

        when:
        def ergebnis = haushaltsbuch.kontoSuchen("Anfangsbestand")

        then:
        ergebnis != null
    }
}
