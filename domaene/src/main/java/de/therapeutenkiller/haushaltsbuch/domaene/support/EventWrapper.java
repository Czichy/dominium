package de.therapeutenkiller.haushaltsbuch.domaene.support;

public class EventWrapper<T> {
    public final String id;
    public final Domänenereignis<T> ereignis;
    public final int version;
    public final String name;

    public EventWrapper(final Domänenereignis<T> ereignis, final int version, final String name) {
        this.id = String.format("%s(%d)", name, version);
        this.ereignis = ereignis;
        this.version = version;
        this.name = name;
    }
}