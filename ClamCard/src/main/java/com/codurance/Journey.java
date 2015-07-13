package com.codurance;

public class Journey {
    private final Station source;
    private final Station destination;

    public Journey(Station source, Station destination) {
        this.source = source;
        this.destination = destination;
    }

    public Money cost() {
        if (source == destination) {
            return new Money(0);
        }
        if (source.isIn(Zone.B))
            return new Money(3.0);
        return new Money(2.5);
    }

}
