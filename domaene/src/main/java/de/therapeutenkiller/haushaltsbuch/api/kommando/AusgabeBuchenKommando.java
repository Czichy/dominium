package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.dominium.aggregat.Wertobjekt;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class AusgabeBuchenKommando extends Wertobjekt {
    public final UUID haushaltsbuchId;
    public final String sollkonto;
    public final String habenkonto;
    public final MonetaryAmount währungsbetrag;

    public AusgabeBuchenKommando(
            final UUID haushaltsbuchId,
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount währungsbetrag) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.währungsbetrag = währungsbetrag;
    }
}
