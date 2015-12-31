package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.api.kommando.AusgabeBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class AusgabeBuchen {

    private final HaushaltsbuchRepository repository;
    private final Event<BuchungWurdeAbgelehnt> buchungWurdeAbgelehntEvent;
    private final Event<BuchungWurdeAusgeführt> buchungWurdeAusgeführtEvent;

    @Inject
    public AusgabeBuchen(
            final HaushaltsbuchRepository repository,
            final Event<BuchungWurdeAbgelehnt> buchungWurdeAbgelehntEvent,
            final Event<BuchungWurdeAusgeführt> buchungWurdeAusgeführtEvent) {
        this.repository = repository;

        this.buchungWurdeAbgelehntEvent = buchungWurdeAbgelehntEvent;
        this.buchungWurdeAusgeführtEvent = buchungWurdeAusgeführtEvent;
    }

    public void ausführen(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount betrag) {
        final Haushaltsbuch haushaltsbuch = this.repository.findBy(haushaltsbuchId);

        haushaltsbuch.ausgabeBuchen(sollkonto, habenkonto, betrag);
        repository.save(haushaltsbuch);
    }

    public void process(@Observes final AusgabeBuchenKommando kommando) {
        this.ausführen(
                kommando.haushaltsbuchId,
                kommando.sollkonto,
                kommando.habenkonto,
                kommando.währungsbetrag
        );
    }
}
