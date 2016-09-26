package com.dhex.shipping.dao;

import com.dhex.shipping.model.Country;

import java.util.List;

public interface CountryDao {
    Country insert(String countryName);
    boolean update(Country country);
    Country getCountry(long id);
    List<Country> listCountriesByStatus(Boolean enabled);

    List<Country> listAll();
}
