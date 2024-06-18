package org.example.processor;

import org.example.entity.ProductEntity;
import org.example.entity.TypeEntity;
import org.example.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

class FakeProductRepository implements ProductRepository {
    private final List<ProductEntity> products = List.of(
            new ProductEntity("Apple", BigDecimal.valueOf(1.49), TypeEntity.SNACK),
            new ProductEntity("Coffee", BigDecimal.valueOf(4.99), TypeEntity.DRINK));

    @Override
    public ProductEntity findByName(String name) {
        return products.stream()
                .filter(p -> p.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}

