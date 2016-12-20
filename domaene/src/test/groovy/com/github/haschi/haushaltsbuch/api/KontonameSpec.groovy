package com.github.haschi.haushaltsbuch.api

import nl.jqno.equalsverifier.EqualsVerifier
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification


class KontonameSpec extends Specification {

    def "Kontoname darf nicht leer sein"() {
        when:
        Kontobezeichnung.of("")

        then:
        thrown KontobezeichnungWarUngültig
    }

    def "Kontoname darf nicht länger als 128 Zeichen sein"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        thrown KontobezeichnungWarUngültig

        where:
        kontoname | _
        "X" * 130 | _
        "X" * 129 | _
        "X" * 999 | _
    }

    def "Kontoname zwischen 1 und 128 Zeichen sind erlaubt"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        notThrown KontobezeichnungWarUngültig

        where:
        kontoname | _
        "X" * 1   | _
        "X" * 128 | _
    }

    def "Kontoname darf nicht ausschließlich Leerzeichen enthalten"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        thrown KontobezeichnungWarUngültig

        where:
        kontoname             | _
        StringUtils.SPACE     | _
        StringUtils.SPACE * 2 | _
        StringUtils.LF        | _
    }

    def "Kontoname darf Leerzeichen enthalten"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        notThrown KontobezeichnungWarUngültig

        where:
        kontoname                           | _
        "Ein Konto"                         | _
        "X" + StringUtils.SPACE * 126 + "X" | _
    }

    def "Kontoname darf Umlaute enthalten"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        notThrown KontobezeichnungWarUngültig

        where:
        kontoname | _
        "öäüÖÄüß" | _
        "ÜÄ"      | _
    }

    def "Kontoname ist ein Wert"() {
        expect:
        EqualsVerifier.forClass(Kontobezeichnung).verify()
    }

    def "Kontoname hat Zeichenkettenrepräsentation"() {
        when:
        def kontoname = Kontobezeichnung.of("Ein Konto")

        then:
        kontoname.toString() == "Ein Konto"
    }
}