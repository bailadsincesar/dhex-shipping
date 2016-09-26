package com.dhex.shipping.controllers;

import com.dhex.shipping.builders.CityBuilder;
import com.dhex.shipping.dtos.ListOf;
import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.City;
import com.dhex.shipping.services.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {

    @Mock
    private CityService cityService;
    private CityController controller;

    @Before
    public void setUp() throws Exception {
        controller = new CityController(cityService);
    }

    @Test
    public void shouldReturn201IfCityWasCreated() throws URISyntaxException {
        HttpStatus responseStatusCode = controller.create("Andahuaylas").getStatusCode();

        assertThat(responseStatusCode, is(CREATED));
    }

    @Test
    public void shouldReturnTheCity() throws URISyntaxException {
        when(cityService.create("Jauja"))
                .thenReturn(CityBuilder.create().with(c -> c.name = "Jauja").now());

        ResponseEntity<City> response = controller.create("Jauja");

        assertThat(response.getBody().getName(), is("Jauja"));
    }

    @Test
    public void shouldReturn400OnDuplicatedEntity() throws URISyntaxException {
        ResponseEntity responseEntity = controller.handle(new DuplicatedEntityException("Any message"));

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
        assertThat(responseEntity.getBody(), is("City is duplicated"));
    }

    @Test
    public void shouldReturn200WhenListing() {
        ResponseEntity responseEntity = controller.list();

        assertThat(responseEntity.getStatusCode(), is(OK));
    }

    @Test
    public void shouldReturnAListOfCitiesWhenListing() {
        List<City> expectedCities = asList(
                CityBuilder.create().with(c -> c.name = "Chancay").now(),
                CityBuilder.create().with(c -> c.name = "Paita").now()
        );
        when(cityService.list())
                .thenReturn(expectedCities);

        ResponseEntity<ListOf<City>> responseEntity = controller.list();

        List<City> cities = responseEntity.getBody().getList();
        assertThat(cities, is(expectedCities));
    }
}
