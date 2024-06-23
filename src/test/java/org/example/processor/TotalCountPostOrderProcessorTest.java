package org.example.processor;

import org.example.model.ExtraItem;
import org.example.model.ProductItem;
import org.example.model.Receipt;
import org.example.model.Type;
import org.example.service.processor.TotalCountPostOrderProcessor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TotalCountPostOrderProcessorTest {
    private TotalCountPostOrderProcessor totalCountPostOrderProcessor;

    @Test
    public void testProcessOrderValidOrder() {
        // given
        totalCountPostOrderProcessor = new TotalCountPostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.valueOf(1.29), List.of()),
                                new ProductItem("Coffee",Type.DRINK,  BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of(
                                        new ExtraItem("Milk", BigDecimal.valueOf(0.50), BigDecimal.valueOf(0.19)),
                                        new ExtraItem("Sugar", BigDecimal.valueOf(0.25), BigDecimal.ZERO)
                                ))
                        ), BigDecimal.ZERO
                )
        );

        // when
        Receipt receipt = totalCountPostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // then
        assertNotNull(receipt);
        assertEquals(2, receipt.products().size());
        assertEquals(BigDecimal.valueOf(5.75), receipt.total());
    }

    @Test
    public void testProcessOrderValidOrderWithoutExtras() {
        // given
        totalCountPostOrderProcessor = new TotalCountPostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(
                                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.valueOf(1.29), List.of()),
                                new ProductItem("Coffee", Type.DRINK,  BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of())
                        ), BigDecimal.ZERO
                )
        );

        // when
        Receipt receipt = totalCountPostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // then
        assertNotNull(receipt);
        assertEquals(2, receipt.products().size());
        assertEquals(BigDecimal.valueOf(5.19), receipt.total());
    }

    @Test
    public void testProcessOrderValidOrderWithoutProducts() {
        // given
        totalCountPostOrderProcessor = new TotalCountPostOrderProcessor(
                // fake order processor
                order -> new Receipt(
                        List.of(), BigDecimal.ZERO
                )
        );

        // when
        Receipt receipt = totalCountPostOrderProcessor
                // can be anything, since order processor mocked with data
                .processOrder(null);

        // then
        assertNotNull(receipt);
        assertEquals(0, receipt.products().size());
        assertEquals(BigDecimal.valueOf(0), receipt.total());
    }
}
