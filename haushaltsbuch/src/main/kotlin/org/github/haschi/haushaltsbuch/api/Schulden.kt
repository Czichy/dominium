package org.github.haschi.haushaltsbuch.api

import org.javamoney.moneta.function.MonetaryFunctions
import javax.money.MonetaryAmount

data class Schulden(private val l: List<Schuld>) : List<Schuld> by l {

    val summe: Währungsbetrag
    get() = Währungsbetrag(
            this.stream()
                    .map<MonetaryAmount> { m -> m.währungsbetrag.wert }
                    .reduce(MonetaryFunctions.sum())
                    .orElse(Währungsbetrag.NullEuro.wert))

    companion object {
        val keine: Schulden
            get() = Schulden(emptyList())
    }
}

