package com.example.application.dtos.command;

import java.util.UUID;

public record OrderedItem(UUID productId, int quantity) {}