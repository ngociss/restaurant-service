package com.example.domain.entity;


public abstract class AggregateRoot<T> {
    private T id;

    public void setId(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}