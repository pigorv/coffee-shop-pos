package org.example.repository;

import org.example.entity.ExtraEntity;

public interface ExtraRepository {
    ExtraEntity findByName(String name);
}
