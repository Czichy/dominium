package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.dominium.aggregat.Wertobjekt;

import java.io.Serializable;

public final class KontoWurdeAngelegt extends Wertobjekt implements HaushaltsbuchEreignis, Serializable {

    private static final long serialVersionUID = 1L;

    public final String kontoname;
    public final Kontoart kontoart;

    public KontoWurdeAngelegt(final String kontoname, final Kontoart kontoart) {

        super();

        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }

    @Override
    public void anwendenAuf(final Haushaltsbuch aggregat) {
        aggregat.falls(this);
    }
}