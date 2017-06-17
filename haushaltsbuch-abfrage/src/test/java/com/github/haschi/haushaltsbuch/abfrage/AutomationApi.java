package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;

import java.util.UUID;

public interface AutomationApi
{
    void start();

    void stop();

    void haushaltsführungBegonnen(
            AggregateProxy<HaushaltsbuchTestaggregat> aggregat,
            ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt);

    Haushaltsbuch haushaltsbuch(UUID identifier);
}