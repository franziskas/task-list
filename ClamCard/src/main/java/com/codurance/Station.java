package com.codurance;

public enum Station {
    ALDGATE(Zone.A),
    ASTERISK(Zone.A),
    BARBICAN(Zone.B),
    BALHAM(Zone.B),
    ANGEL(Zone.A);

    private Zone zone;

    Station(Zone zone) {
        this.zone = zone;
    }

    public boolean isIn(Zone zone) {
        return this.zone == zone;
    }

}
