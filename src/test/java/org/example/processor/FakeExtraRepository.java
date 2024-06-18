package org.example.processor;

import org.example.entity.ExtraEntity;
import org.example.repository.ExtraRepository;

import java.math.BigDecimal;
import java.util.List;

class FakeExtraRepository implements ExtraRepository {
    private final List<ExtraEntity> extras = List.of(
            new ExtraEntity("Milk", BigDecimal.valueOf(0.50)),
            new ExtraEntity("Sugar", BigDecimal.valueOf(0.25)));

    @Override
    public ExtraEntity findByName(String name) {
        return extras.stream()
                .filter(e -> e.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
