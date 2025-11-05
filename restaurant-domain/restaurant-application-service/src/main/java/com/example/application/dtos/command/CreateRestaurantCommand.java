package com.example.application.dtos.command;

public record CreateRestaurantCommand(
        String name,
        boolean isActive
) {}

