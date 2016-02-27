package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.Set;
import java.util.UUID;

public final class HaushaltsbuchSchnappschuss implements Schnappschuss<Haushaltsbuch, UUID, HaushaltsbuchEreignisziel> {
    public final long version;
    private final UUID identität;
    public ImmutableSet<Konto> konten;
    public ImmutableList<Set<Buchungssatz>> buchungssätze;

    public HaushaltsbuchSchnappschuss(final UUID identität, final long version) {
        this.identität = identität;
        this.version = version;
    }

    @Override
    public final UUID getIdentitätsmerkmal() {
        return this.identität;
    }

    @Override
    public final long getVersion() {
        return this.version;
    }

    @Override
    public Haushaltsbuch wiederherstellen() {
        return new Haushaltsbuch(this);
    }
}
