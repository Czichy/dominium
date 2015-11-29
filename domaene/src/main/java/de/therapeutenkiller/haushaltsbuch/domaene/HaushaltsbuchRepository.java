package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import java.util.UUID;

public interface HaushaltsbuchRepository {
    void hinzufügen(final Haushaltsbuch haushaltsbuch);

    Haushaltsbuch besorgen(UUID haushaltsbuchId);

    void leeren();
}
