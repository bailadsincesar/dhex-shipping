package com.dhex.shipping.dao;

import com.dhex.shipping.model.City;

import java.util.List;

public interface CityDao {
    City insert(String countryName);

    List<City> listAll();
}
