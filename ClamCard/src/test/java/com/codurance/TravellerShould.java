package com.codurance;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TravellerShould {
    @Test
    public void
    be_billed_the_price_of_two_journies_starting_and_ending_at_zone_a() {
        Traveller traveller = new Traveller();

        traveller.make(new Journey(Station.ALDGATE, Station.ASTERISK));
        traveller.make(new Journey(Station.ASTERISK, Station.ANGEL));

        assertThat(traveller.bill(), is(new Money(5.00)));
    }

    @Test
    public void
    be_billed_nothing_if_there_are_no_journeys() {
        Traveller traveller = new Traveller();
        
        assertThat(traveller.bill(), is(new Money(0.00)));
    }
}
