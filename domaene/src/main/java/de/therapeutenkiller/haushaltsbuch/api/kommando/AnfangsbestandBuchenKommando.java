package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.support.Wertobjekt;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class AnfangsbestandBuchenKommando extends Wertobjekt {
    public final UUID haushaltsbuchId;
    public final String kontoname;
    public final MonetaryAmount währungsbetrag;

    public AnfangsbestandBuchenKommando(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount währungsbetrag) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.währungsbetrag = währungsbetrag;
    }
}
