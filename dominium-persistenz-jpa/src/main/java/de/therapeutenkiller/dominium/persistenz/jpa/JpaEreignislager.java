package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("checkstyle:designforextension")
public class JpaEreignislager<E extends Domänenereignis<T>, T> implements Ereignislager<E, UUID, T> {

    private final EntityManager entityManager;
    private final Uhr uhr;

    @Inject
    public JpaEreignislager(final EntityManager entityManager, final Uhr uhr) {

        this.entityManager = entityManager;
        this.uhr = uhr;
    }

    @Transactional

    public void neuenEreignisstromErzeugen(
            final UUID identitätsmerkmal,
            final Collection<E> domänenereignisse) {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(identitätsmerkmal);

        for (final Domänenereignis<T> ereignis : domänenereignisse) {
            this.entityManager.persist(ereignis);
            this.entityManager.persist(ereignisstrom.registrieren(ereignis));
        }

        this.entityManager.persist(ereignisstrom);

    }

    @Transactional

    public void ereignisseDemStromHinzufügen(
            final UUID identitätsmerkmal,
            final long erwarteteVersion,
            final Collection<E> domänenereignisse)
            throws KonkurrierenderZugriff {

        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, identitätsmerkmal);

        if (strom.getVersion() != erwarteteVersion) {
            throw new KonkurrierenderZugriff(erwarteteVersion, strom.getVersion());
        }

        for (final E ereignis : domänenereignisse) {
            this.entityManager.persist(ereignis);
            this.entityManager.persist(strom.registrieren(ereignis));
        }
    }

    public List<E> getEreignisliste(final UUID identitätsmerkmal, final Versionsbereich bereich) {
        final TypedQuery<JpaDomänenereignisUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaDomänenereignisUmschlag i "
                        + "WHERE i.meta.identitätsmerkmal = :identitätsmerkmal "
                        + "AND i.meta.version >= :vonVersion "
                        + "AND i.meta.version <= :bisVersion "
                        + "ORDER BY i.meta.version",
                JpaDomänenereignisUmschlag.class);

        query.setParameter("vonVersion", bereich.getVon());
        query.setParameter("bisVersion", bereich.getBis());
        query.setParameter("identitätsmerkmal", identitätsmerkmal);

        final List<JpaDomänenereignisUmschlag> resultList = query.getResultList();

        return resultList.stream()
                .map(JpaDomänenereignisUmschlag<E>::öffnen)
                .collect(Collectors.toList());
    }

    @Transactional
    /*
    public void schnappschussHinzufügen(final UUID identitätsmerkmal, final S snapshot)
            throws AggregatNichtGefunden {
        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, identitätsmerkmal);
        if (strom == null) {
            throw new AggregatNichtGefunden();
        }

        final JpaSchnappschussUmschlag<A, T> umschlag = new JpaSchnappschussUmschlag<>(
                identitätsmerkmal,
                this.uhr.jetzt(),
                snapshot);

        this.entityManager.persist(umschlag);
    }
    */

    /*
    public Optional<Schnappschuss<A, UUID>> getNeuesterSchnappschuss(
            final UUID identitätsmerkmal)
            throws AggregatNichtGefunden {
        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, identitätsmerkmal);
        if (strom == null) {
            throw new AggregatNichtGefunden();
        }

        final TypedQuery<JpaSchnappschussUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaSchnappschussUmschlag i "
                        + "WHERE i.meta.identitätsmerkmal = :identitätsmerkmal "
                        + "ORDER BY i.meta.erstellungszeitpunkt DESC",
                JpaSchnappschussUmschlag.class);

        final List<JpaSchnappschussUmschlag> resultList = query
                .setParameter("identitätsmerkmal", identitätsmerkmal)
                .setMaxResults(1)
                .getResultList();


        final Stream<Schnappschuss<A, UUID>> schnappschussStream = resultList.stream()
                .map(JpaSchnappschussUmschlag<A, T>::öffnen);

        return schnappschussStream.findFirst();
    }
    */

    @Override
    public Stream<UUID> getEreignisströme() {
        throw new NotImplementedException("Die Methode ist nicht implementiert.");
    }
}
