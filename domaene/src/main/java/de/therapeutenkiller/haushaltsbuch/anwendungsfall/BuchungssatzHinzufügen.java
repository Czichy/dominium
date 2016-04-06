package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.FügeBuchungssatzHinzu;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Stateless
@SuppressWarnings("checkstyle:designforextension")
public class BuchungssatzHinzufügen {

    @Inject
    private HaushaltsbuchRepository repository;

    @Inject
    public BuchungssatzHinzufügen(final HaushaltsbuchRepository repository) {
        super();

        this.repository = repository;
    }

    public BuchungssatzHinzufügen() {
        super();
    }

    public void ausführen(@Observes final FügeBuchungssatzHinzu befehl)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {

        final Haushaltsbuch haushaltsbuch = this.repository.suchen(befehl.identitätsmerkmal);
        final Buchungssatz buchungssatz = new Buchungssatz(befehl.sollkonto, befehl.habenkonto, befehl.betrag);
        haushaltsbuch.buchungssatzHinzufügen(buchungssatz);

        this.repository.speichern(haushaltsbuch);
    }
}
