package com.dhex.shipping.dao;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.City;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

public class CityBasicDaoTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private CityDao cityDao;

    @Before
    public void setUp() throws Exception {
        cityDao = new CityBasicDao();
    }

    @Test
    public void shouldReturnCityIfCreateIsSuccessfully() {
        // act
        City city = cityDao.insert("Omate");

        // assert
        assertThat(city.getId(), is(1L));
        assertThat(city.getName(), is("Omate"));
        assertThat(city.isEnabled(), is(true));
    }

    @Test
    public void shouldGenerateASequentialIdEachNewCity() {
        // act
        City city1 = cityDao.insert("Chimbote");
        City city2 = cityDao.insert("Cañete");

        // assert
        assertThat(city1.getId() + 1, is(city2.getId()));
    }

    @Test
    public void shouldThrowAnExceptionOnCreatingWithDuplicatedName() {
        // assert
        expectedException.expect(DuplicatedEntityException.class);
        expectedException.expectMessage("City name Omate already exists");

        // act
        cityDao.insert("Omate");
        cityDao.insert("Omate");
    }

    @Test
    public void shouldListAllTheCitiesInsertedWhenListing() {
        cityDao.insert("Pisco");
        cityDao.insert("Nazca");

        List<City> countries = cityDao.listAll();

        assertThat(countries, hasItems(
                hasProperty("name", is("Nazca")),
                hasProperty("name", is("Pisco"))));
    }
}