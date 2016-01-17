package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.support.Wertobjekt;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class EinnahmeBuchenKommando extends Wertobjekt {
    public final UUID haushaltsbuchId;
    public final String sollkonto;
    public final String habenkonto;
    public final MonetaryAmount währungsbetrag;

    public EinnahmeBuchenKommando(
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
