package com.github.haschi.domain.haushaltsbuch.modell.core.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Inventar

data class ErfasseInventar(
        @TargetAggregateIdentifier val id: Aggregatkennung,
        val inventar: Inventar)
