package org.example.processor;

import org.example.model.Extra;
import org.example.model.OrderList;
import org.example.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderListValidatorTest {
    private final FakeProductRepository fakeProductRepository = new FakeProductRepository();
    private final FakeExtraRepository fakeExtraRepository = new FakeExtraRepository();
    private final OrderListValidator orderListValidator =
            new OrderListValidator(fakeProductRepository, fakeExtraRepository);

    @Test
    public void testValidateNoErrorsForValidOrderList() {
        // given
        OrderList orderList = new OrderList(List.of(
                new Product("Apple", List.of()),
                new Product("Coffee", List.of(new Extra("Milk"), new Extra("Sugar")))
        ));

        // when / then
        orderListValidator.validate(orderList);
    }

    @Test
    public void testValidateNullOrderList() {
        assertThrows(NullPointerException.class,
                () -> orderListValidator.validate(null));
    }

    @Test
    public void testValidateEmptyOrderList() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderListValidator.validate(new OrderList(List.of())));
        assertEquals("Order must have products", exception.getMessage());
    }

    @Test
    public void testValidateProductNotFound() {
        // given
        OrderList orderList = new OrderList(List.of(
                new Product("NonExistingProduct", List.of())
        ));

        // when / then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderListValidator.validate(orderList));
        assertEquals("There is no such product as NonExistingProduct", exception.getMessage());
    }

    @Test
    public void testValidateExtraNotFound() {
        // given
        OrderList orderList = new OrderList(List.of(
                new Product("Coffee", List.of(new Extra("NonExistingExtra")))
        ));

        // when / then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderListValidator.validate(orderList));
        assertEquals("There is no such extra as NonExistingExtra", exception.getMessage());
    }
}