package org.example.processor;

import org.example.model.ExtraItem;
import org.example.model.ProductItem;
import org.example.model.Receipt;
import org.example.model.Type;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FreeBeveragePostOrderProcessorTest {
    private FreeBeveragePostOrderProcessor freeBeveragePostOrderProcessor;

    @Test
    public void testProcessOrderLessThanNFreeBeverage() {
        // given
        freeBeveragePostOrderProcessor = new FreeBeveragePostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of(
                                        new ExtraItem("Milk", BigDecimal.valueOf(0.50), BigDecimal.ZERO),
                                        new ExtraItem("Sugar", BigDecimal.valueOf(0.25), BigDecimal.ZERO)
                                ))
                        ), BigDecimal.ZERO
                ));

        // when
        Receipt receipt = freeBeveragePostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // then
        assertNotNull(receipt);
        assertEquals(2, receipt.products().size());

        // Verify no discount applied
        receipt.products().forEach(item -> {
            assertEquals(BigDecimal.ZERO, item.discount());
            item.extras().forEach(extra -> assertEquals(BigDecimal.ZERO, extra.discount()));
        });
    }

    @Test
    public void testProcessOrderEqualToNFreeBeverage() {
        // given
        freeBeveragePostOrderProcessor = new FreeBeveragePostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(
                                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of())
                        ), BigDecimal.ZERO
                ));

        // when
        Receipt receipt = freeBeveragePostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // then
        assertNotNull(receipt);
        assertEquals(5, receipt.products().size());
        assertDiscountWereApplied(receipt);
    }

    @Test
    public void testProcessOrderMoreThanNFreeBeverage() {
        // given
        freeBeveragePostOrderProcessor = new FreeBeveragePostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee",Type.DRINK,  BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee",Type.DRINK,  BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee",Type.DRINK,  BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee",Type.DRINK,  BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee",Type.DRINK,  BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of()),
                                new ProductItem("Coffee",Type.DRINK,  BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of())
                        ), BigDecimal.ZERO
                ));

        // when
        Receipt receipt = freeBeveragePostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // then
        assertNotNull(receipt);
        assertEquals(7, receipt.products().size());
        assertDiscountWereApplied(receipt);
    }

    @Test
    public void testProcessOrderNoBeveragesInOrder() {
        // given
        freeBeveragePostOrderProcessor = new FreeBeveragePostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of()),
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of())
                        ), BigDecimal.ZERO
                ));

        // when
        Receipt receipt = freeBeveragePostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // then
        assertNotNull(receipt);
        assertEquals(6, receipt.products().size());
        // Verify no discount applied
        receipt.products().forEach(item -> {
            assertEquals(BigDecimal.ZERO, item.discount());
            item.extras().forEach(extra -> assertEquals(BigDecimal.ZERO, extra.discount()));
        });
    }

    @Test
    public void testProcessOrderNoProducts() {
        // given
        freeBeveragePostOrderProcessor = new FreeBeveragePostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(), BigDecimal.ZERO
                )
        );

        // when
        Receipt receipt = freeBeveragePostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // then
        assertNotNull(receipt);
        assertEquals(0, receipt.products().size());
    }

    private void assertDiscountWereApplied(Receipt receipt) {
        // Verify discount applied to every Nth beverage
        int count = 0;
        for (int i = 0; i < receipt.products().size(); i++) {
            ProductItem item = receipt.products().get(i);
            if (item.type() == Type.DRINK) {
                count++;
            }
            if (count != 0 && count % 5 == 0) {
                assertEquals(item.price(), item.discount());
            } else {
                assertEquals(BigDecimal.ZERO, item.discount());
            }
        }
    }

}