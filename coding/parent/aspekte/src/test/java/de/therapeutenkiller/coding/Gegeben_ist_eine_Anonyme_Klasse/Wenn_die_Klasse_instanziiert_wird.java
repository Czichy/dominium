package de.therapeutenkiller.coding.Gegeben_ist_eine_Anonyme_Klasse;

import org.testng.annotations.Test;

/**
 * Created by m.haschka on 11/5/15.
 */
public class Wenn_die_Klasse_instanziiert_wird {

    @Test
    public void dann_wird_keine_Ausnahme_ausgelöst() {
        new Object() { { } };
    }
}
