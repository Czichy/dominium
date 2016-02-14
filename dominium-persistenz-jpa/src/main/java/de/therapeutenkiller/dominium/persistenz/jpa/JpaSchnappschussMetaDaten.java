package de.therapeutenkiller.dominium.persistenz.jpa;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class JpaSchnappschussMetaDaten implements Serializable {
    final String streamName;
    final LocalDateTime erstellungszeitpunkt;

    public JpaSchnappschussMetaDaten(final String streamName, final LocalDateTime jetzt) {
        this.streamName = streamName;
        this.erstellungszeitpunkt = jetzt;
    }

    protected JpaSchnappschussMetaDaten() {
        this.streamName = StringUtils.EMPTY;
        this.erstellungszeitpunkt = null;
    }
}