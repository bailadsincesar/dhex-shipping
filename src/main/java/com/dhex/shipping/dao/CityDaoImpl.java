package com.dhex.shipping.dao;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.exceptions.NoExistingEntityException;
import com.dhex.shipping.model.City;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by angel on 25/09/16.
 */
@Component
public class CityDaoImpl {

    private static final String DUPLICATED_NAME_ERROR_MESSAGE = "City name %s already exists";
    private static final String NON_EXISTING_CITY_ID_ERROR_MESSAGE = "Non existing city ID: %s";
    private static Set<City> cities = new HashSet<>();
    private static long sequentialId = 0;

    public CityDaoImpl() {
        ++sequentialId;
    }

    public City insert(City city) {
        City createdCity = new City(sequentialId, city.getName(), city.getActivityIndicator(), city.getCountryCode());
        boolean wasAdded = cities.add(createdCity);

        if (!wasAdded) {
            String errorMessage = String.format(DUPLICATED_NAME_ERROR_MESSAGE, city.getName());
            throw new DuplicatedEntityException(errorMessage);
        }

        return createdCity;
    }

    public boolean update(City city) {
        if(null != findCity(city.getId())){
            return cities.add(city);
        }
        return false;
    }

    public City getCity(long id) {
        return findCity(id);
    }

    public List<City> listAll() {
        return new ArrayList<>(cities);
    }

    private City findCity(long cityId){
        City city = null;
        for (City c: cities) {
            if(cityId == c.getId())
                return city;
        }
        String errorMessage = String.format(NON_EXISTING_CITY_ID_ERROR_MESSAGE, cityId);
        throw new NoExistingEntityException(errorMessage);
    }

}

