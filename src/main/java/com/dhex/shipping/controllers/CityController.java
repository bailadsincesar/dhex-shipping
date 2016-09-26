package com.dhex.shipping.controllers;

import com.dhex.shipping.dtos.ListOf;
import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.exceptions.NoExistingEntityException;
import com.dhex.shipping.model.City;
import com.dhex.shipping.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.dhex.shipping.dtos.ListOf.createListOf;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(allowCredentials = "true", value = "*", methods = {GET, POST, PUT}, allowedHeaders = "*")
@RequestMapping("/cities")
public class CityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);
    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<City> create(@RequestBody City city) throws URISyntaxException {
        City createdCity = cityService.create(city);
        return ResponseEntity
                .created(new URI("/City/" + city.getName()))
                .body(createdCity);
    }

    @RequestMapping(method = PUT)
    public ResponseEntity<City> update(@RequestBody City city) throws URISyntaxException {
        cityService.update(city);
        return ResponseEntity.ok(city);
    }

    @ExceptionHandler(value = DuplicatedEntityException.class)
    public ResponseEntity handle(DuplicatedEntityException ex) {
        LOGGER.info("City already existed.", ex);
        return ResponseEntity.badRequest().body("City is duplicated");
    }

    @ExceptionHandler(value = NoExistingEntityException.class)
    public ResponseEntity handle(NoExistingEntityException ex) {
        LOGGER.info("Non existing City ID.", ex);
        return ResponseEntity.badRequest().body("City was not found.");
    }

    @RequestMapping(method = GET)
    public ResponseEntity<ListOf<City>> list() {
        List<City> cities = cityService.list();
        return ResponseEntity.ok(createListOf(cities));
    }

    @RequestMapping(value = "/id/{id}", method = GET)
    public ResponseEntity<City> get(@PathVariable("id") long id) {
        City city = cityService.get(id);
        return ResponseEntity.ok(city);
    }
}