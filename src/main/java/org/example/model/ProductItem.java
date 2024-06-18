package org.example.model;

import java.math.BigDecimal;
import java.util.List;

public record ProductItem(String name,
                          Type type,
                          BigDecimal price,
                          BigDecimal discount,
                          List<ExtraItem> extras) {
}
