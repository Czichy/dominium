package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractEröffnungsbilanzSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Buchung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableErstelleEröffnungsbilanz;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableEröffnungsbilanzkontoErstellt;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Inventar;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.config.Configuration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EröffnungsbilanzSteps implements AbstractEröffnungsbilanzSteps
{

    private final Configuration configuration;
    private final Ereignismonitor monitor;
    private final Aggregatkennung haushaltsbuchId;
    private final Inventar inventar;

    public EröffnungsbilanzSteps(final Configuration configuration, final Ereignismonitor monitor, final Aggregatkennung
            haushaltsbuchId, final Inventar inventar) {
        this.configuration = configuration;

        this.monitor = monitor;
        this.haushaltsbuchId = haushaltsbuchId;
        this.inventar = inventar;
    }

    @Override
    public void erstellen()
    {
        configuration.commandGateway().sendAndWait(
                ImmutableErstelleEröffnungsbilanz.builder()
                        .id(haushaltsbuchId)
                        .inventar(inventar)
                        .build());

        monitor.erwarte(1);
    }

    @Override
    public void erstellt(final List<Buchung> buchungen)
    {
        monitor.erwartetesEreignis(0, ist -> assertThat(ist).isEqualTo(
                ImmutableEröffnungsbilanzkontoErstellt.builder()
                .addAllBuchungen(buchungen)
                .build()));
    }
}
