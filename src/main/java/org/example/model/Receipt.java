package org.example.model;

import java.math.BigDecimal;
import java.util.List;

public record Receipt(List<ProductItem> products, BigDecimal total) {
}
