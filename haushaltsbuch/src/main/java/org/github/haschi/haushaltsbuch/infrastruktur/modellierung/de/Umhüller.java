package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import org.immutables.value.Value;

@Deprecated
public abstract class Umhüller<T>
{
    @Value.Parameter
    public abstract T wert();

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" + wert() + ")";
    }
}