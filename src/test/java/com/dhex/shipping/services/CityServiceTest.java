package com.dhex.shipping.services;

import com.dhex.shipping.builders.CityBuilder;
import com.dhex.shipping.dao.CityDao;
import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.model.City;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    private CityService cityService;
    @Mock
    private CityDao cityDao;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        cityService = new CityService(cityDao);
    }

    @Test
    public void shouldGenerateACityAfterCreate() {
        // arrange
        String cityName = "Chepen";
        long expectedId = 1000000000L;
        City createdOnDaoCity = CityBuilder
                .create()
                .with(c -> c.id = expectedId)
                .with(c -> c.name = cityName)
                .now();
        when(cityDao.insert(cityName))
                .thenReturn(createdOnDaoCity);

        // act
        City city = cityService.create(cityName);

        // assert
        assertThat(city.isEnabled(), is(true));
        assertThat(city.getId(), is(expectedId));
        assertThat(city.getName(), is(cityName));
    }

    @Test
    public void shouldHaveNameAsNotNullWhenCreatingCity() {
        // assert
        expectedException.expect(InvalidArgumentDhexException.class);
        expectedException.expectMessage("Name of the city should not be empty");

        // act
        cityService.create(null);
    }

    @Test
    public void shouldHaveNameAsNotEmptyWhenCreatingCity() {
        // assert
        expectedException.expect(InvalidArgumentDhexException.class);
        expectedException.expectMessage("Name of the city should not be empty");

        // act
        cityService.create(" ");
    }

    @Test
    public void shouldThrowAnExceptionIfNameIsLarge() {
        // assert
        expectedException.expect(InvalidArgumentDhexException.class);
        expectedException.expectMessage("Name of the city should not be greater than 100 chars");

        // act
        String largeCityName = "blablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla";
        cityService.create(largeCityName);
    }

    @Test
    public void shouldRetrieveAListOfCitiesWhenListing() {
        List<City> expectedCities = asList(
                CityBuilder.create().with(c -> c.name = "Mollendo").now(),
                CityBuilder.create().with(c -> c.name = "Camana").now());
        when(cityDao.listAll()).thenReturn(expectedCities);
        List<City> countries = cityService.list();
        assertThat(countries, is(expectedCities));
    }
}
