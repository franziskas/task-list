package com.codurance;

public class Journey {
    private final Station source;
    private final Station destination;

    public Journey(Station source, Station destination) {
        this.source = source;
        this.destination = destination;
    }

    public Money fare() {
        if (thereIsNoJourney()) {
            return new Money(0);
        }

        if (touchesZoneB()) {
            return new Money(3.0);
        }

        return new Money(2.5);
    }

    private boolean thereIsNoJourney() {
        return source == destination;
    }

    private boolean touchesZoneB() {
        return source.isIn(Zone.B) || destination.isIn(Zone.B);
    }

}
