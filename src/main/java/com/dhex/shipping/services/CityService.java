package com.dhex.shipping.services;

import com.dhex.shipping.dao.CityDaoImpl;
import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by angel on 25/09/16.
 */
@Component
public class CityService {
    private static final String EMPTY_NAME_ERROR_MESSAGE = "Name of the City should not be empty";
    private static final String LARGE_NAME_ERROR_MESSAGE = "Name of the City should not be greater than 100 chars";
    private CityDaoImpl CityDao;

    @Autowired
    public CityService(CityDaoImpl CityDao) {
        this.CityDao = CityDao;
    }

    public City create(City city) {
        validateName(city.getName());
        return CityDao.insert(city);
    }

    public boolean update(City City){
        validateName(City.getName());
        return CityDao.update(City);
    }

    public City get(long id){
        return CityDao.getCity(id);
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
        return CityDao.listAll();
    }
}

