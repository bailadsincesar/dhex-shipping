package com.dhex.shipping.dao;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.exceptions.NoExistingEntityException;
import com.dhex.shipping.model.Country;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CountryBasicDao implements CountryDao {

    private static final String DUPLICATED_NAME_ERROR_MESSAGE = "Country name %s already exists";
    private static final String NON_EXISTING_COUNTRY_ID_ERROR_MESSAGE = "Non existing country ID: %s";
    private Set<Country> countries;
    private long sequentialId;

    public CountryBasicDao() {
        countries = new HashSet<>();
        sequentialId = 0;
    }

    @Override
    public Country insert(String countryName) {
        Country country = new Country(++sequentialId, countryName, true);
        boolean wasAdded = countries.add(country);

        if (!wasAdded) {
            String errorMessage = String.format(DUPLICATED_NAME_ERROR_MESSAGE, country.getName());
            throw new DuplicatedEntityException(errorMessage);
        }

        return country;
    }

    @Override
    public boolean update(Country country) {
        if(null != findCountry(country.getId())){
            return countries.add(country);
        }
        return false;
    }

    @Override
    public Country getCountry(long id) {
        return findCountry(id);
    }

    @Override
    public List<Country> listAll() {

        return new ArrayList<>(countries);
    }

    @Override
    public List<Country> listCountriesByStatus(Boolean enabled){
        List<Country> countriesResult = new ArrayList<>();
        for (Country c: countries) {
            if(enabled.equals(c.isEnabled()))
                countriesResult.add(c);
        }
        return countriesResult;
    }


    private Country findCountry(long countryId){
        for (Country c: countries) {
            if(countryId == c.getId())
                return c;
        }
        String errorMessage = String.format(NON_EXISTING_COUNTRY_ID_ERROR_MESSAGE, countryId);
        throw new NoExistingEntityException(errorMessage);
    }

}
