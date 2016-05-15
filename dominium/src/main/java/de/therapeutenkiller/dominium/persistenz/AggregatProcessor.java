package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

import java.util.List;

public interface AggregatProcessor<I, T> {
    void apply(final I identitätsmerkmal, final List<Domänenereignis<T>> änderungen, long initialversion);
}
