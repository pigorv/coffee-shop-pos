package org.example.model;

import java.math.BigDecimal;

public record ExtraItem(String name,
                        BigDecimal price,
                        BigDecimal discount) {
}
