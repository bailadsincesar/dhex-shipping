package com.dhex.shipping.controllers;

import com.dhex.shipping.Application;
import com.dhex.shipping.model.ActivityIndicator;
import com.dhex.shipping.model.City;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturn201WhenCityIsCreated() {
        City city = new City("Santiago", ActivityIndicator.ENABLED, 1);
        createCity(city).then()
                .assertThat()
                .statusCode(CREATED.value())
                .header("Location", "/City/Santiago")
                .body("name", is("Santiago"));
    }

    @Test
    public void shouldReturn400WhenCityAlreadyExists() {
        City city1 = new City("Rio", ActivityIndicator.ENABLED, 2);
        createCity(city1);
        City city2 = new City("Rio", ActivityIndicator.ENABLED, 2);
        createCity(city2).then()
                .assertThat()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn200WhenCityIsUpdated() throws Exception {
        City city1 = new City("BuenosAires", ActivityIndicator.ENABLED, 3);
        createCity(city1);
        City city2 = new City("Lina", ActivityIndicator.ENABLED, 4);
        createCity(city2);
        updateCity(new City(1, "Lima", ActivityIndicator.ENABLED, 4))
        .then()
                .assertThat()
                .statusCode(OK.value());
    }

    private Response updateCity(City city) {
        return given()
                .contentType("application/json")
                .body(city)
        .when()
                .put("/cities");
    }

    @Test
    public void shouldReturn404WhenCityNotExist() throws Exception {
         updateCity(new City(24, "Lima", ActivityIndicator.ENABLED, 4))
         .then()
                .assertThat()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn200WhenCountriesAreListed() {
        City city1 = new City("Bogota", ActivityIndicator.ENABLED, 5);
        createCity(city1);
        City city2 = new City("Quito", ActivityIndicator.ENABLED, 6);
        createCity(city2);
        City city3 = new City("Caracas", ActivityIndicator.ENABLED, 7);
        createCity(city3);

        when()
                .get("/cities")
        .then()
                .assertThat()
                .statusCode(OK.value())
            .and()
                .body("list.name", hasItems("Caracas", "Quito", "Bogota"));
    }

    @Test
    public void shouldReturn404WhenCityIsNotFound() throws Exception {
        given()
                .body(300L)
        .when()
                .get("/cities/id")
        .then()
                .assertThat()
                .statusCode(BAD_REQUEST.value());
    }

    private Response createCity(City city) {
        return given()
                .contentType("application/json")
                .body(city)
        .when()
                .post("/cities");
    }
}
