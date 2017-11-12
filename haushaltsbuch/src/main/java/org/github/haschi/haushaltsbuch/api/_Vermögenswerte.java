package org.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Eingehüllt;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Umhüller;
import org.immutables.value.Value;
import org.javamoney.moneta.function.MonetaryFunctions;

import java.util.Collections;
import java.util.List;

@Value.Immutable
@Eingehüllt
@JsonSerialize(as = Vermögenswerte.class)
@JsonDeserialize(as = Vermögenswerte.class)
public abstract class _Vermögenswerte extends Umhüller<List<Vermoegenswert>>
{
    public final Währungsbetrag summe()
    {
        return Währungsbetrag.of(
                wert().stream()
                        .map(m -> m.währungsbetrag().wert())
                        .reduce(MonetaryFunctions.sum())
                        .orElse(Währungsbetrag.NullEuro().wert()));
    }

    public static Vermögenswerte leer()
    {
        return Vermögenswerte.of(Collections.emptyList());
    }
}
