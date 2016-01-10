package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis
import de.therapeutenkiller.haushaltsbuch.domaene.support.MemoryEreignisstrom
import spock.lang.Specification

class EreignisstromFürAggregatErzeugen extends Specification {
    def "Ein Ereignisstrom kann für eine Aggregatwurzel erzeugt werden"() {
        given:
        Haushaltsbuch haushaltsbuch = new Haushaltsbuch(UUID.randomUUID());

        when:
        MemoryEreignisstrom<Haushaltsbuch> strom = new MemoryEreignisstrom<>(haushaltsbuch);

        then:
        Domänenereignis<Haushaltsbuch> ereignis = Mock()
        strom.umschlagErzeugen(ereignis, 1).version == 1
    }
}
