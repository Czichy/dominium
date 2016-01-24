package de.therapeutenkiller.haushaltsbuch.persistenz

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import de.therapeutenkiller.dominium.jpa.JpaEreignisstrom
import de.therapeutenkiller.haushaltsbuch.api.Kontoart
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HaushaltsbuchWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt
import spock.lang.Specification

import javax.persistence.EntityManager


class HibernateHaushaltsbuchRepositoryTest extends Specification {

    def "Repository benötigt einen JPA Entity Manager"() {
        when:
        new HibernateHaushaltsbuchRepository(null)

        then:
        thrown ArgumentIstNullException
    }

    def "Für neue Aggregate wird ein Ereignisstrom angelegt"() {

        given: "Angenommen ich habe ein Repository und ein Haushaltsbuch"
        EntityManager entityManager = Mock(EntityManager)
        def hhb = new Haushaltsbuch(UUID.randomUUID())
        HibernateHaushaltsbuchRepository repository = new HibernateHaushaltsbuchRepository(entityManager);

        when: "Wenn ich das Haushaltsbuch dem Repository hinzufüge"
        repository.add hhb

        then: "Dann wird der JpaEreignisstrom gespeichert"
        1 * entityManager.persist({
            it.equals(new JpaEreignisstrom<Haushaltsbuch>(Haushaltsbuch.class.getName()+"-"+hhb.identitätsmerkmal.toString()))
        })
    }

    def "Für neue Aggregate werden die aufgetretenen Ereignisse gespeichert"() {

        given: "Angenommen ich habe ein Repository und ein neues Haushaltsbuch"
        EntityManager entityManager = Mock(EntityManager)
        def haushaltsbuch = new Haushaltsbuch(UUID.randomUUID())
        def repository = new HibernateHaushaltsbuchRepository(entityManager)

        when: "Wenn ich das Haushaltsbuch dem Repository hinzufüge"
        repository.add haushaltsbuch

        then:
        1 * entityManager.persist(new HaushaltsbuchWurdeAngelegt(haushaltsbuch.identitätsmerkmal))
    }

    def "Für bestehende aggregate werden die aufgetretenen Ereignisse gespeichert"() {
        given:
        EntityManager entityManager = Mock(EntityManager)
        def haushaltsbuch = new Haushaltsbuch(UUID.randomUUID())
        def repository = new HibernateHaushaltsbuchRepository(entityManager);
        repository.add haushaltsbuch

        when: "Wenn ich die Änderungen des Haushaltsbuches speicher"
        haushaltsbuch.neuesKontoHinzufügen "Girokonto", Kontoart.Aktiv
        repository.save haushaltsbuch

        then:
        1 * entityManager.persist(new HaushaltsbuchWurdeAngelegt(haushaltsbuch.identitätsmerkmal))

        then:
        1 * entityManager.persist(new KontoWurdeAngelegt("Girokonto", Kontoart.Aktiv))
    }
}
