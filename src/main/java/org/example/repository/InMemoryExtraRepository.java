package org.example.repository;

import org.example.entity.ExtraEntity;

import java.math.BigDecimal;
import java.util.Map;

public class InMemoryExtraRepository implements ExtraRepository {
    private static final Map<String, ExtraEntity> STORAGE = Map.of(
            "extra milk", new ExtraEntity("extra milk", BigDecimal.valueOf(0.32)),
            "foamed milk", new ExtraEntity("foamed milk", BigDecimal.valueOf(0.51)),
            "special roast coffee", new ExtraEntity("special roast coffee", BigDecimal.valueOf(0.95))
    );

    @Override
    public ExtraEntity findByName(String name) {
        return STORAGE.get(name.toLowerCase());
    }
}
