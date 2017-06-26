package com.github.haschi.haushaltsbuch.abfrage.rest;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.ImmutableHaushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.Main;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import cucumber.api.Scenario;
import net.thucydides.core.annotations.Step;
import org.wildfly.swarm.Swarm;

import java.util.UUID;

public class RestAutomationApi implements AutomationApi
{

    private Swarm swarm;
    private Scenario scenario;
    private boolean started;

    @Override
    @Step
    public void start()
    {
        try
        {
            // swarm = Main.createSwarm("-Djava.util.logging.manager=org.jboss.logmanager.LogManager");
            swarm = Main.createSwarm();
            swarm.start();
            started = true;
            swarm.deploy();

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    @Step
    public void stop()
    {
        try
        {
            if (started) swarm.stop();
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    @Step
    public void haushaltsführungBegonnen(
            AggregateProxy<HaushaltsbuchTestaggregat> aggregat,
            ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt)
    {

    }

    @Override
    public Haushaltsbuch haushaltsbuch(UUID identifier)
    {
        return ImmutableHaushaltsbuch
                .builder()
                .build();
    }

    @Override
    public String requiredTag()
    {
        return "@api";
    }
}
