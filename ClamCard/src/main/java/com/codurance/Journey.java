package com.codurance;

public class Journey {
    private final Station source;
    private final Station destination;

    public Journey(Station source, Station destination) {
        this.source = source;
        this.destination = destination;
    }

    public double getPrice() {
        if (source == destination) {
            return 0;
        }
        return 2.5;
    }
}
