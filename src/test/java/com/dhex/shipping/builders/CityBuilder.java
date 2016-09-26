package com.dhex.shipping.builders;

import com.dhex.shipping.model.City;

import java.util.function.Consumer;

public final class CityBuilder {
    public long id = 1L;
    public String name = "Huancayo";
    public boolean enabled = true;

    private CityBuilder() {
        // to avoid unexpected instantiations
    }

    public static CityBuilder create() {
        return new CityBuilder();
    }

    public CityBuilder with(Consumer<CityBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public City now() {
        return new City(id, name, enabled);
    }
}