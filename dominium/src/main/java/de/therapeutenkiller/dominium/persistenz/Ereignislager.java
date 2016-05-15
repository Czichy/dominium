package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Versionsbereich;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Dauerhafte Ablage für Domänenereignisse.
 *
 */
public interface Ereignislager<I, T> {

    void neuenEreignisstromErzeugen(
            I identitätsmerkmal,
            Collection<Domänenereignis<T>> domänenereignisse);

    void ereignisseDemStromHinzufügen(
            I identitätsmerkmal,
            long erwarteteVersion, Collection<Domänenereignis<T>> domänenereignisse)
        throws KonkurrierenderZugriff, EreignisstromWurdeNichtGefunden;

    List<Domänenereignis<T>> getEreignisliste(I identitätsmerkmal, Versionsbereich bereich);

    /**
     * @return Liefert die Identitätsmerkmale aller Ereignisströme des Lagers.
     */
    Stream<I> getEreignisströme();

    boolean existiertEreignisStrom(I identitätsmerkmal);
}
