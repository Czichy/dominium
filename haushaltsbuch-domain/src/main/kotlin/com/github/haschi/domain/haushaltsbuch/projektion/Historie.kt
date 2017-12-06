package com.github.haschi.domain.haushaltsbuch.projektion

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import org.axonframework.eventsourcing.DomainEventMessage

interface Historie
{
    fun bezüglich(aggregat: Aggregatkennung): Sequence<DomainEventMessage<*>>
}