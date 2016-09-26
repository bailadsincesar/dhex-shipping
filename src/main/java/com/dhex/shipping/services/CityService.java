package com.dhex.shipping.services;

import com.dhex.shipping.dao.CityDao;
import com.dhex.shipping.dao.CountryDao;
import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.model.City;
import com.dhex.shipping.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CityService {
    private static final String EMPTY_NAME_ERROR_MESSAGE = "Name of the city should not be empty";
    private static final String LARGE_NAME_ERROR_MESSAGE = "Name of the city should not be greater than 100 chars";
    private CityDao cityDao;

    @Autowired
    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public City create(String name) {
        validateName(name);
        return cityDao.insert(name);
    }

    private void validateName(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new InvalidArgumentDhexException(EMPTY_NAME_ERROR_MESSAGE);
        }
        if(name.length() > 100) {
            throw new InvalidArgumentDhexException(LARGE_NAME_ERROR_MESSAGE);
        }
    }

    public List<City> list() {
        return cityDao.listAll();
    }
}
