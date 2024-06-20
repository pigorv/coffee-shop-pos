package org.example.entity;

import java.math.BigDecimal;

public record ProductEntity(String name, BigDecimal price, TypeEntity type) {
}
