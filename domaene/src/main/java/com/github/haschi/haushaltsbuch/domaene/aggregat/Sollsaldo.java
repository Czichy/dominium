package com.github.haschi.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public final class Sollsaldo extends Saldo {
    @Override
    public MonetaryAmount getBetrag() {
        return this.betrag;
    }

    public Sollsaldo(final MonetaryAmount betrag) {
        super(betrag);
    }

    @Override
    public String toString() {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.getBetrag()); // NOPMD LoD TODO

        return "Sollsaldo{" + betrag + "}";
    }
}