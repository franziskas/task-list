package com.codurance;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ClamCardShould {

    @Test
    public void
    charge_nothing_if_you_enter_and_leave_at_the_same_station() {
        Journey journey = new Journey(Station.ALDGATE, Station.ALDGATE);

        assertThat(journey.cost(), is(new Money(0.0)));
    }

    @Test
    public void
    charge_two_fifty_for_a_one_way_journey_starting_and_ending_in_zone_a() {
        Journey journey = new Journey(Station.ASTERISK, Station.ALDGATE);

        assertThat(journey.cost(), is(new Money(2.50)));
    }

    @Test
    public void
    charge_three_for_a_one_way_journey_starting_and_ending_in_zone_b() {
        Journey journey = new Journey(Station.BARBICAN, Station.BALHAM);

        assertThat(journey.cost(), is(new Money(3.00)));
    }
}
