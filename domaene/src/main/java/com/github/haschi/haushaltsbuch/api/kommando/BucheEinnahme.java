package com.github.haschi.haushaltsbuch.api.kommando;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.immutables.value.Value;

import javax.money.MonetaryAmount;
import java.util.UUID;

// @Value.Style(generateSuppressAllWarnings = true, jdkOnly = true, passAnnotations = TargetAggregateIdentifier.class)
@Value.Immutable
public interface BucheEinnahme
{

    @TargetAggregateIdentifier
    UUID haushaltsbuchId();

    String sollkonto();

    String habenkonto();

    MonetaryAmount waehrungsbetrag();
}
