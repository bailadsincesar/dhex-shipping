package com.dhex.shipping.controllers;

import com.dhex.shipping.Application;
import com.dhex.shipping.model.Country;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryControllerIntegrationTesting {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturn201WhenCountryIsCreated() {
        createCountry("Chile").then()
                .assertThat()
                .statusCode(CREATED.value())
                .header("Location", "/country/Chile")
                .body("name", is("Chile"));
    }

    @Test
    public void shouldReturn400WhenCountryAlreadyExists() {
        createCountry("Brasil");
        createCountry("Brasil").then()
                .assertThat()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn200WhenCountryIsUpdated() throws Exception {
        createCountry("Argentina");
        createCountry("Pedu");
        updateCountry(new Country(4, "Peru", true))
        .then()
                .assertThat()
                .statusCode(OK.value());
    }

    private Response updateCountry(Country country) {
        return given()
                .contentType("application/json")
                .body(country)
        .when()
                .put("/countries");
    }

    @Test
    public void shouldReturn404WhenCountryNotExist() throws Exception {
         updateCountry(new Country(24, "Peru", true))
         .then()
                .assertThat()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn200WhenCountriesAreListed() {
        createCountry("Colombia");
        createCountry("Ecuador");
        createCountry("Venezuela");

        when()
                .get("/countries")
        .then()
                .assertThat()
                .statusCode(OK.value())
            .and()
                .body("list.name", hasItems("Venezuela", "Ecuador", "Colombia"));
    }

    @Test
    public void shouldReturn404WhenCountryIsNotFound() throws Exception {
        given()
                .body(300L)
        .when()
                .get("/countries/id")
        .then()
                .assertThat()
                .statusCode(BAD_REQUEST.value());
    }

    private Response createCountry(String countryName) {
        return given()
                .body(countryName)
        .when()
                .post("/countries");

    }
}
