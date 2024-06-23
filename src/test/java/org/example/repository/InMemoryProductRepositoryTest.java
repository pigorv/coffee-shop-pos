package org.example.repository;

import org.example.entity.ProductEntity;
import org.example.entity.TypeEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryProductRepositoryTest {

    private final InMemoryProductRepository repository = new InMemoryProductRepository();;

    @Test
    public void testFindByNameExistingProduct() {
        ProductEntity product = repository.findByName("small coffee");
        assertNotNull(product);
        assertEquals("small coffee", product.name());
        assertEquals(BigDecimal.valueOf(2.55), product.price());
        assertEquals(TypeEntity.DRINK, product.type());
    }

    @Test
    public void testFindByNameCaseInsensitive() {
        ProductEntity product = repository.findByName("Small Coffee".toLowerCase());
        assertNotNull(product);
        assertEquals("small coffee", product.name());
    }

    @Test
    public void testFindByNameDifferentNamesForSameProduct() {
        ProductEntity product1 = repository.findByName("freshly squeezed orange juice");
        ProductEntity product2 = repository.findByName("orange juice");
        assertNotNull(product1);
        assertNotNull(product2);
        assertEquals(product1, product2);
        assertEquals("orange juice", product1.name());
        assertEquals(BigDecimal.valueOf(4.53), product1.price());
        assertEquals(TypeEntity.DRINK, product1.type());
    }

    @Test
    public void testFindByNameNonExistentProduct() {
        assertThrows(NullPointerException.class, () -> repository.findByName("non-existent product"));
    }
}
