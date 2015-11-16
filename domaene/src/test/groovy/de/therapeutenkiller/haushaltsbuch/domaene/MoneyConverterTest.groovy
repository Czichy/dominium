package de.therapeutenkiller.haushaltsbuch.domaene

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import spock.lang.Specification
import spock.lang.Unroll

import javax.money.format.MonetaryParseException

class MoneyConverterTest  extends Specification {

    @Unroll
    def "Umwandlung gültiger Währungsbeträge"() {
        given: "Angenommen ich habe einen MoneyConverter"
        def converter = new MoneyConverter();

        when: "Wenn ich einen gültigen Währungsbetrag transformiere"
        def result = converter.transform(zeichenfolge)

        then: "Dann erhalte ich ein Money-Objekt mit Betrag und Währung"
        result.currency.currencyCode == währung;
        result.number.toBigDecimal() == betrag

        where:
        zeichenfolge | betrag | währung
        "123,45 EUR" | 123.45 | "EUR"
        "-10,23 USD" | -10.23 | "USD"
        "1  DEM"     | 1.00   | "DEM"
    }

    @Unroll
    def "Umwandlung ungültiger Währungsbeträge"() {

        given: "Angenommen ich habe einen MoneyConverter"
        def converter = new MoneyConverter();

        when: "Wenn ich einen ungültigen Währungsbetrag konvertiere"
        converter.transform(zeichenfolge)

        then: "Denn erhalte ich eine Ausnahme"
        Exception e = thrown()
        e.getClass() == ausnahme

        where:
        zeichenfolge  | ausnahme
        "12.00 €"     | IllegalArgumentException.class
        "Hello World" | MonetaryParseException.class
        ""            | IllegalArgumentException.class
    }

    def "Umwandlung einer null-Referenz fürt zu einem Contract Fehler"() {
        given: "Angenommen ich habe einen MoneyConverter"
        def converter = new MoneyConverter();

        when: "Wenn ich eine null-Referenz transformiere"
        converter.transform(null)

        then: "Dann wird eine ContractExeption Ausnahme ausgelöst"
        thrown ArgumentIstNullException
    }
}
