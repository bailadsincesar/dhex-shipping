package com.dhex.shipping.controllers;

import com.dhex.shipping.dtos.ListOf;
import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.City;
import com.dhex.shipping.model.Country;
import com.dhex.shipping.services.CityService;
import com.dhex.shipping.services.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.dhex.shipping.dtos.ListOf.createListOf;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(allowCredentials = "true", value = "*", methods = {GET, POST}, allowedHeaders = "*")
@RequestMapping("/cities")
public class CityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);
    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<City> create(@RequestBody String cityName) throws URISyntaxException {
        City city= cityService.create(cityName);
        return ResponseEntity
                .created(new URI("/city/" + cityName))
                .body(city);
    }

    @ExceptionHandler(value = DuplicatedEntityException.class)
    public ResponseEntity handle(DuplicatedEntityException ex) {
        LOGGER.info("City already existed.", ex);
        return ResponseEntity.badRequest().body("City is duplicated");
    }

    @RequestMapping(method = GET)
    public ResponseEntity<ListOf<City>> list() {
        List<City> cities = cityService.list();
        return ResponseEntity.ok(createListOf(cities));
    }
}
