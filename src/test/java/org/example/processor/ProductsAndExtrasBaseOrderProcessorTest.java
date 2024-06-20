package org.example.processor;

import org.example.model.Extra;
import org.example.model.ExtraItem;
import org.example.model.OrderList;
import org.example.model.Product;
import org.example.model.ProductItem;
import org.example.model.Receipt;
import org.example.repository.ExtraRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductsAndExtrasBaseOrderProcessorTest {
    private final ProductRepository fakeProductRepository = new FakeProductRepository();
    private final ExtraRepository fakeExtraRepository = new FakeExtraRepository();
    private final OrderProcessor orderProcessor =
            new ProductsAndExtrasBaseOrderProcessor(fakeProductRepository, fakeExtraRepository);

    @Test
    public void testProcessOrderSuccess() {
        // given
        OrderList order = new OrderList(List.of(
                new Product("Apple", List.of()),
                new Product("Coffee", List.of(new Extra("Milk"), new Extra("Sugar")))
        ));

        // when
        Receipt receipt = orderProcessor.processOrder(order);

        // then
        assertNotNull(receipt);
        List<ProductItem> productItems = receipt.products();
        assertEquals(2, productItems.size());

        ProductItem appleItem = productItems.stream()
                .filter(item -> item.name().equals("Apple"))
                .findFirst().orElse(null);
        assertNotNull(appleItem);
        assertEquals(BigDecimal.valueOf(1.49), appleItem.price());
        assertEquals(BigDecimal.ZERO, appleItem.discount());
        assertTrue(appleItem.extras().isEmpty());

        ProductItem coffeeItem = productItems.stream()
                .filter(item -> item.name().equals("Coffee"))
                .findFirst().orElse(null);
        assertNotNull(coffeeItem);
        assertEquals(BigDecimal.valueOf(4.99), coffeeItem.price());
        assertEquals(BigDecimal.ZERO, coffeeItem.discount());
        assertEquals(2, coffeeItem.extras().size());

        ExtraItem milk = coffeeItem.extras().stream()
                .filter(extra -> extra.name().equals("Milk"))
                .findFirst().orElse(null);
        assertNotNull(milk);
        assertEquals(BigDecimal.valueOf(0.5), milk.price());
        assertEquals(BigDecimal.ZERO, milk.discount());

        ExtraItem sugar = coffeeItem.extras().stream()
                .filter(extra -> extra.name().equals("Sugar"))
                .findFirst().orElse(null);
        assertNotNull(sugar);
        assertEquals(BigDecimal.valueOf(0.25), sugar.price());
        assertEquals(BigDecimal.ZERO, sugar.discount());

    }

    @Test
    public void testProcessOrderEmptyOrder() {
        // given
        OrderList emptyOrder = new OrderList(List.of());

        // when
        Receipt receipt = orderProcessor.processOrder(emptyOrder);

        // then
        assertNotNull(receipt);
        assertTrue(receipt.products().isEmpty());
    }
}
