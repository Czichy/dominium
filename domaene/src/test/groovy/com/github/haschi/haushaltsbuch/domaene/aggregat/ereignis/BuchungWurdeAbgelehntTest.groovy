package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification

class BuchungWurdeAbgelehntTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect:
        EqualsVerifier.forClass ImmutableBuchungWurdeAbgelehnt suppress(Warning.NULL_FIELDS) verify()
    }
}
