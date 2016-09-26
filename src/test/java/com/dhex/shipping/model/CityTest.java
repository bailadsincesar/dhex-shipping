package com.dhex.shipping.model;

import com.dhex.shipping.builders.CityBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CityTest {

    @Test
    public void shouldReturnTrueIfTwoCitiesHasSameName() {
        // act
        City trujillo = CityBuilder.create().with(c -> c.name = "Trujillo").now();
        City trujilloWithDifferentId = CityBuilder.create().with(c -> c.name = "Trujillo").with(c -> c.id = 2L).now();
        boolean comparison = trujillo.equals(trujilloWithDifferentId);

        // assert
        assertThat(comparison, is(true));
    }

    @Test
    public void shouldReturnFalseIfTwoCountriesHaveDifferentNames() {
        // act
        City juliaca = CityBuilder.create().with(c -> c.name = "Juliaca").now();
        City chongos = CityBuilder.create().with(c -> c.name = "Chongos Altos").with(c -> c.id = 2L).now();
        boolean comparison = juliaca.equals(chongos);

        // assert
        assertThat(comparison, is(false));
    }
}