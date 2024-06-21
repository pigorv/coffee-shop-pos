package org.example.repository;

import org.example.entity.ExtraEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * The {@code InMemoryExtraRepository} class implements the {@link ExtraRepository} interface
 * and provides an in-memory storage for extra entities.
 * It allows retrieving extra entities by their name.
 */
public class InMemoryExtraRepository implements ExtraRepository {
    private static final Map<String, ExtraEntity> STORAGE = Map.of(
            "extra milk", new ExtraEntity("extra milk", BigDecimal.valueOf(0.32)),
            "foamed milk", new ExtraEntity("foamed milk", BigDecimal.valueOf(0.51)),
            "special roast coffee", new ExtraEntity("special roast coffee", BigDecimal.valueOf(0.95))
    );

    /**
     * Finds an {@link ExtraEntity} by its name.
     * The search is case-insensitive.
     *
     * @param name the name of the extra entity to be retrieved.
     * @return the {@link ExtraEntity} with the specified name, or {@code null} if no such entity exists.
     */
    @Override
    public ExtraEntity findByName(String name) {
        return STORAGE.get(name.toLowerCase());
    }
}
