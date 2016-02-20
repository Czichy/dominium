package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.api.kommando.EinnahmeBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.DieWelt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;

@Singleton
public final class EinnahmeBuchenSteps {

    private final DieWelt kontext;

    @Inject
    public EinnahmeBuchenSteps(final DieWelt kontext) {
        this.kontext = kontext;
    }

    @Wenn("^ich meine Einnahme von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void ich_meine_einnahme_per_an_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto) {

        this.kontext.kommandoAusführen(new EinnahmeBuchenKommando(
                this.kontext.getAktuelleHaushaltsbuchId(),
                sollkonto,
                habenkonto,
                währungsbetrag));
    }
}
