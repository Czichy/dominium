package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import cucumber.runtime.java.picocontainer.PicoFactory
import io.vertx.core.logging.LoggerFactory

class KomponententestPicoFactory : PicoFactory()
{
    init
    {
        logger.info("Initialisiere Komponententest Pico Factory")
        addClass(Testcloud::class.java)
        addClass(Welt::class.java)
        addClass(AxonInfrastrukturFactory::class.java)
        addClass(Anwendungskonfiguration::class.java)
    }

    companion object
    {
        val logger = LoggerFactory.getLogger(KomponententestPicoFactory::class.java)
    }
}