package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;

@Entity
public class ZustandWurdeGeändert extends TestAggregatEreignis {

    private long payload;

    private ZustandWurdeGeändert() {

    }

    public ZustandWurdeGeändert(final long payload) {
        this.payload = payload;
    }

    public final long getPayload() {
        return this.payload;
    }

    @Override
    public final void anwendenAuf(final TestAggregatEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("payload", payload)
                .toString();
    }
}
