package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.HaushaltsbuchAngelegt;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Haushaltsbuchverzeichnis
{
    static private Logger log = LoggerFactory.getLogger(Haushaltsbuchverzeichnis.class);

    private final Map<Aggregatkennung, Haushaltsbuch> haushaltsbücher = new ConcurrentHashMap<>();

    public Haushaltsbuchverzeichnis()
    {
    }

    @EventHandler
    public void falls(final HaushaltsbuchAngelegt ereignis) {
        log.info("Ereignis HaushaltsbuchAngelegt");
        haushaltsbücher.put(ereignis.id(),
                            ImmutableHaushaltsbuch.builder()
                                    .id(ereignis.id())
                                    .build());
    }

    public Optional<Haushaltsbuch> suchen(final Aggregatkennung haushaltsbuchId) {
        log.info("Haushaltsbuch suchen");

        if(haushaltsbücher.containsKey(haushaltsbuchId))
        {
            return Optional.of(haushaltsbücher.get(haushaltsbuchId));
        }

        return Optional.empty();
    }
}
