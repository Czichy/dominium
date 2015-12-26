package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public class Buchungssatz {

    private final Konto sollkonto;
    private final Konto habenkonto;
    private final MonetaryAmount währungsbetrag;

    public Buchungssatz(final Konto sollkonto, final Konto habenkonto, final MonetaryAmount währungsbetrag) {
        if (währungsbetrag.isNegative()) {
            throw new IllegalArgumentException("Buchungssätze dürfen keine negativen Beträge besitzen.");
        }

        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.währungsbetrag = währungsbetrag;
    }

    public final boolean sollst(final Konto konto) {
        return this.sollkonto.equals(konto);
    }

    public final boolean hatHabenkonto(final Konto konto) {
        return this.habenkonto.equals(konto);
    }

    public final Konto getSollkonto() {
        return this.sollkonto;
    }

    public final Konto getHabenkonto() {
        return this.habenkonto;
    }

    public final MonetaryAmount getWährungsbetrag() {
        return this.währungsbetrag;
    }

    @Override
    public final boolean equals(@DarfNullSein final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Buchungssatz that = (Buchungssatz) obj;

        return new EqualsBuilder()
            .append(this.sollkonto, that.sollkonto)
            .append(this.habenkonto, that.habenkonto)
            .append(this.währungsbetrag, that.währungsbetrag)
            .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.sollkonto)
            .append(this.habenkonto)
            .append(this.währungsbetrag)
            .toHashCode();
    }

    public final boolean istAnfangsbestandFür(final Konto konto) {
        return this.habenkonto.equals(konto) && this.sollkonto.equals(Konto.ANFANGSBESTAND)
                || this.habenkonto.equals(Konto.ANFANGSBESTAND) && this.sollkonto.equals(konto);
    }

    @Override
    public String toString() {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.währungsbetrag);

        return String.format("%s (%s) an %s (%s)",
                sollkonto.getBezeichnung(),
                betrag,
                habenkonto.getBezeichnung(),
                betrag);
    }
}
