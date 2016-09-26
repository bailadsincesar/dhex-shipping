package com.dhex.shipping.model;

import java.util.Objects;

/**
 * Represents a city.
 */
public class City {
    private long id;
    private String name;
    private ActivityIndicator indicator;
    private long countryCode;

    public City(){
        //Empty constructor
    }

    public City(String name, ActivityIndicator indicator, long countryId) {
        this.countryCode = countryId;
        this.name = name;
        this.indicator = indicator;
    }

    public City(long id, String name, ActivityIndicator indicator, long countryId) {
        this.countryCode = countryId;
        this.id = id;
        this.name = name;
        this.indicator = indicator;
    }

    public ActivityIndicator getActivityIndicator() {
        return indicator;
    }

    public long getId() {
        return id;
    }

    public long getCountryCode() {
        return countryCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
