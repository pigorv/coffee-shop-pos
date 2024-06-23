package org.example;

import org.example.service.extractor.DefaultOrderListExtractor;
import org.example.service.extractor.OrderListExtractor;
import org.example.service.extractor.OrderListExtractorException;
import org.example.model.Extra;
import org.example.model.OrderList;
import org.example.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultOrderListExtractorTest {
    private final OrderListExtractor extractor = new DefaultOrderListExtractor();

    @Test
    void testValidOrderWithMultipleProductsAndExtras() {
        String input = "large coffee with extra milk and sugar, orange juice with ice";
        OrderList result = extractor.extractOrderList(input);

        assertEquals(2, result.products().size());

        Product firstProduct = result.products().get(0);
        assertEquals("large coffee", firstProduct.name());
        assertEquals(2, firstProduct.extras().size());
        assertTrue(firstProduct.extras().contains(new Extra("extra milk")));
        assertTrue(firstProduct.extras().contains(new Extra("sugar")));

        Product secondProduct = result.products().get(1);
        assertEquals("orange juice", secondProduct.name());
        assertEquals(1, secondProduct.extras().size());
        assertTrue(secondProduct.extras().contains(new Extra("ice")));
    }

    @Test
    void testValidOrderWithSingleProductAndExtras() {
        String input = "espresso with milk";
        OrderList result = extractor.extractOrderList(input);

        assertEquals(1, result.products().size());

        Product product = result.products().get(0);
        assertEquals("espresso", product.name());
        assertEquals(1, product.extras().size());
        assertTrue(product.extras().contains(new Extra("milk")));
    }

    @Test
    void testValidOrderWithProductsWithoutExtras() {
        String input = "espresso, black coffee";
        OrderList result = extractor.extractOrderList(input);

        assertEquals(2, result.products().size());

        Product firstProduct = result.products().get(0);
        assertEquals("espresso", firstProduct.name());
        assertTrue(firstProduct.extras().isEmpty());

        Product secondProduct = result.products().get(1);
        assertEquals("black coffee", secondProduct.name());
        assertTrue(secondProduct.extras().isEmpty());
    }

    @Test
    void testEmptyExtras() {
        String input = "espresso with ";
        assertThrows(OrderListExtractorException.class, () -> extractor.extractOrderList(input));
    }

    @Test
    void testEmptyInput() {
        String input = "";
        assertThrows(OrderListExtractorException.class, () -> extractor.extractOrderList(input));
    }

    @Test
    void testNullInput() {
        String input = null;
        assertThrows(OrderListExtractorException.class, () -> extractor.extractOrderList(input));
    }

    @Test
    void testMalformedInput() {
        String input = "large coffee with, orange juice with ice";
        assertThrows(OrderListExtractorException.class, () -> extractor.extractOrderList(input));
    }
}