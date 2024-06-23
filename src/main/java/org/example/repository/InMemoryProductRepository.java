package org.example.repository;

import org.example.entity.ProductEntity;
import org.example.entity.TypeEntity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * The {@code InMemoryProductRepository} class implements the {@link ProductRepository} interface
 * and provides an in-memory storage for product entities.
 * It allows retrieving product entities by their name.
 */
public class InMemoryProductRepository implements ProductRepository {
    // STORAGE can keep different names for the same product
    private static final Map<Set<String>, ProductEntity> STORAGE = Map.of(
            Set.of("small coffee"), new ProductEntity("small coffee", BigDecimal.valueOf(2.55), TypeEntity.DRINK),
            Set.of("medium coffee"), new ProductEntity("medium coffee", BigDecimal.valueOf(3.05), TypeEntity.DRINK),
            Set.of("large coffee"), new ProductEntity("large coffee", BigDecimal.valueOf(3.55), TypeEntity.DRINK),
            Set.of("bacon roll"), new ProductEntity("bacon roll", BigDecimal.valueOf(4.53), TypeEntity.SNACK),
            Set.of("freshly squeezed orange juice",
                    "orange juice"), new ProductEntity("orange juice", BigDecimal.valueOf(4.53), TypeEntity.DRINK)
    );

    /**
     * Finds an {@link ProductEntity} by its name.
     * The search is case-insensitive.
     *
     * @param name the name of the product entity to be retrieved.
     * @return the {@link ProductEntity} with the specified name, or {@code null} if no such entity exists.
     */
    @Override
    public ProductEntity findByName(String name) {
        // not optimal O(n) search
        return STORAGE.entrySet().stream()
                .filter(entry -> entry.getKey().contains(name))
                .findFirst()
                .map(Map.Entry::getValue)
                // not found
                .orElseThrow(NullPointerException::new);
    }
}
