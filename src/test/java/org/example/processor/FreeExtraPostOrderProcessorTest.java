package org.example.processor;

import org.example.model.ExtraItem;
import org.example.model.ProductItem;
import org.example.model.Receipt;
import org.example.model.Type;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FreeExtraPostOrderProcessorTest {

    private FreeExtraPostOrderProcessor freeExtraPostOrderProcessor;

    @Test
    public void testProcessOrderFreeExtraForBeverageAndSnack() {
        // Mock order with one beverage and one snack
        freeExtraPostOrderProcessor = new FreeExtraPostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(
                                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of(
                                        new ExtraItem("Milk", BigDecimal.valueOf(0.50), BigDecimal.ZERO))),
                                new ProductItem("Chips", Type.SNACK, BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of(
                                        new ExtraItem("Cheese", BigDecimal.valueOf(0.50), BigDecimal.ZERO)
                                ))
                        ), BigDecimal.ZERO
                ));

        // when
        Receipt receipt = freeExtraPostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // Assertion
        assertNotNull(receipt);
        assertEquals(2, receipt.products().size());

        // Verify extras with discounts applied correctly
        for (ProductItem item : receipt.products()) {
            if (item.name().equals("Coffee")) {
                assertEquals(BigDecimal.valueOf(0.50), item.extras().get(0).discount());
            } else {
                assertEquals(BigDecimal.ZERO, item.extras().get(0).discount());
            }
        }
    }

    @Test
    public void testProcessOrderNotEnoughBeverage() {
        // Mock order with one beverage and one snack
        freeExtraPostOrderProcessor = new FreeExtraPostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(
                                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of(
                                        new ExtraItem("Milk", BigDecimal.valueOf(0.50), BigDecimal.ZERO))),
                                new ProductItem("Water", Type.DRINK, BigDecimal.valueOf(0.99), BigDecimal.ZERO, List.of(
                                ))
                        ), BigDecimal.ZERO
                ));

        // when
        Receipt receipt = freeExtraPostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // Assertion
        assertNotNull(receipt);
        assertEquals(2, receipt.products().size());
        // Verify extras with discounts applied correctly
        for (ProductItem item : receipt.products()) {
            if (!item.extras().isEmpty()) {
                assertEquals(BigDecimal.ZERO, item.extras().get(0).discount());
            }
        }
    }

    @Test
    public void testProcessOrderNotEnoughSnack() {
        freeExtraPostOrderProcessor = new FreeExtraPostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(
                                new ProductItem("Bacon Roll", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.ZERO, List.of(
                                        new ExtraItem("Cheese", BigDecimal.valueOf(0.50), BigDecimal.ZERO))),
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(0.99), BigDecimal.ZERO, List.of(
                                ))
                        ), BigDecimal.ZERO
                ));

        // when
        Receipt receipt = freeExtraPostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // Assertion
        assertNotNull(receipt);
        assertEquals(2, receipt.products().size());
        // Verify extras with discounts applied correctly
        for (ProductItem item : receipt.products()) {
            if (!item.extras().isEmpty()) {
                assertEquals(BigDecimal.ZERO, item.extras().get(0).discount());
            }
        }
    }

    @Test
    public void testProcessOrderNoProducts() {
        // given
        freeExtraPostOrderProcessor = new FreeExtraPostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(), BigDecimal.ZERO
                )
        );

        // when
        Receipt receipt = freeExtraPostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // then
        assertNotNull(receipt);
        assertEquals(0, receipt.products().size());
    }
}