package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configurer;

public interface Systemumgebung
{
    Configurer konfigurieren(Configurer configurer);
}
