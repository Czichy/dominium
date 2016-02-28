package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import java.io.Serializable;
import java.util.UUID;

public final class JournalWurdeAngelegt extends Wertobjekt implements HaushaltsbuchEreignis, Serializable {
    private final UUID aktuelleHaushaltsbuchId;

    public JournalWurdeAngelegt(final UUID aktuelleHaushaltsbuchId) {
        this.aktuelleHaushaltsbuchId = aktuelleHaushaltsbuchId;
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel aggregat) {
        aggregat.falls(this);
    }
}