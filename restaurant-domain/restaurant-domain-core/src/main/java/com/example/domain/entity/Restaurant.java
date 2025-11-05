package com.example.domain.entity;

import com.example.domain.valueobject.RestaurantId;

public class Restaurant extends AggregateRoot<RestaurantId> {

    private final String name;
    private final boolean active;

    public Restaurant(RestaurantId id, String name, boolean active) {
        setId(id);
        this.name = name;
        this.active = active;
    }

    public String getName() { return name; }
    public boolean isActive() { return active; }
}
