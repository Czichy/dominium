package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jgroups.JChannel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class AxonKonfiguration
{
    private EventStorageEngine theEngine;

    public CommandBus setupDistributedCommandBus(final JGroupsConnector connector)
    {
        return new DistributedCommandBus(connector, connector);
    }

    @Produces
    @ApplicationScoped
    public JGroupsConnector createConnector()
    {
        JChannel channel = new JChannel(true);
        CommandBus localCommandBus = new SimpleCommandBus();
        return new JGroupsConnector(
                localCommandBus,
                channel,
                "haushaltsbuch-cluster",
                new XStreamSerializer(),
                new AnnotationRoutingStrategy());
    }

    @Produces
    @ApplicationScoped
    public Configuration konfigurieren(final EventStorageEngine engine, final JGroupsConnector connector) {
        theEngine = engine;
        return DefaultConfigurer.defaultConfiguration()
                .configureCommandBus(c -> setupDistributedCommandBus(connector))
                .configureEmbeddedEventStore(c -> engine)
                .registerCommandHandler(c -> new HaushaltsbuchAnlegenHandler())
                .buildConfiguration();
    }

    @Produces
    @ApplicationScoped
    public EventStorageEngine eventStorageEngine()
    {
        return new InMemoryEventStorageEngine();
    }
}
