package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class JpaDomänenereignis<T> extends Domänenereignis<T> {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    UUID id;

    public JpaDomänenereignis() {
        this.id = UUID.randomUUID();
    }
}
