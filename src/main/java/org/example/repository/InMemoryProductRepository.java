package org.example.repository;

import org.example.entity.ProductEntity;
import org.example.entity.TypeEntity;

import java.math.BigDecimal;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
    private static final Map<String, ProductEntity> STORAGE = Map.of(
            "small coffee", new ProductEntity("small coffee", BigDecimal.valueOf(2.55), TypeEntity.DRINK),
            "medium coffee", new ProductEntity("medium coffee", BigDecimal.valueOf(3.05), TypeEntity.DRINK),
            "large coffee", new ProductEntity("large coffee", BigDecimal.valueOf(3.55), TypeEntity.DRINK),
            "bacon roll", new ProductEntity("bacon roll", BigDecimal.valueOf(4.53), TypeEntity.SNACK),
            "freshly squeezed orange juice", new ProductEntity("freshly squeezed orange juice", BigDecimal.valueOf(4.53), TypeEntity.DRINK)
    );

    @Override
    public ProductEntity findByName(String name) {
        return STORAGE.get(name.toLowerCase());
    }
}
