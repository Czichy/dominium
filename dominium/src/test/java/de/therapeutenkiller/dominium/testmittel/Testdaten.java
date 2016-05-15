package de.therapeutenkiller.dominium.testmittel;

import de.therapeutenkiller.dominium.modell.Version;
import de.therapeutenkiller.dominium.testdomäne.TestAggregatSchnappschuss;

import java.util.UUID;

public class Testdaten {

    public static final long PAYLOAD = 42L;
    public static final Version VERSION = Version.NEU.nachfolger();

    public static TestAggregatSchnappschuss getSchnappschuss(final UUID identitätsmerkmal) {
        return TestAggregatSchnappschuss.builder()
            .identitätsmerkmal(identitätsmerkmal)
            .payload(PAYLOAD)
            .version(VERSION)
            .build();
    }
}
