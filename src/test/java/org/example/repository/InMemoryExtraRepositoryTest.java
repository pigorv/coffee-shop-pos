package org.example.repository;

import org.example.entity.ExtraEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryExtraRepositoryTest {

    private final InMemoryExtraRepository repository  = new InMemoryExtraRepository();;
    @Test
    public void testFindByNameExistingExtra() {
        ExtraEntity extra = repository.findByName("extra milk");
        assertNotNull(extra);
        assertEquals("extra milk", extra.name());
        assertEquals(BigDecimal.valueOf(0.32), extra.price());
    }

    @Test
    public void testFindByNameCaseInsensitive() {
        ExtraEntity extra = repository.findByName("EXTRA MILK");
        assertNotNull(extra);
        assertEquals("extra milk", extra.name());
    }

    @Test
    public void testFindByNameNonExistentExtra() {
        assertNull(repository.findByName("non-existent extra"));
    }
}
