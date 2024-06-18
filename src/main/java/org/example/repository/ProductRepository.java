package org.example.repository;

import org.example.entity.ProductEntity;

public interface ProductRepository {
    ProductEntity findByName(String name);
}
