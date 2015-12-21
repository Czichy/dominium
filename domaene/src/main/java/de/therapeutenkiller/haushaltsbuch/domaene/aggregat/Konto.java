package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

public final class Konto extends Wertobjekt {

    public static final Konto anfangsbestand = new Konto("Anfangsbestand");

    private final String kontoname;

    public Konto(final String kontoname) {

        super();
        this.kontoname = kontoname;
    }

    public String getBezeichnung() {
        return this.kontoname;
    }
}
