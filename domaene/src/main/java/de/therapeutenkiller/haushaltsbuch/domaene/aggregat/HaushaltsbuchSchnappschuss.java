package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.therapeutenkiller.dominium.persistenz.jpa.JpaSchnappschuss;

import java.util.Set;
import java.util.UUID;

public final class HaushaltsbuchSchnappschuss extends JpaSchnappschuss<Haushaltsbuch> {

    private static final long serialVersionUID = -2656820980971196374L;

    public final long version;
    private final UUID identitätsmerkmal;
    public ImmutableSet<Konto> konten;
    public ImmutableList<Set<Buchungssatz>> buchungssätze;

    public HaushaltsbuchSchnappschuss(final UUID identität, final long version) {
        super();
        this.identitätsmerkmal = identität;
        this.version = version;
    }

    @Override
    public UUID getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    @Override
    public long getVersion() {
        return this.version;
    }
}
