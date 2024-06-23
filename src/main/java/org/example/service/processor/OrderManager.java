package org.example.service.processor;

import org.example.model.OrderList;
import org.example.model.Receipt;
import org.example.service.validator.OrderListValidator;

/**
 * The {@code OrderManager} class is responsible for managing the order process,
 * including validating and processing the order list.
 */
public class OrderManager {
    private final OrderListValidator validator;
    private final OrderProcessor orderProcessor;

    public OrderManager(OrderListValidator validator, OrderProcessor orderProcessor) {
        this.validator = validator;
        this.orderProcessor = orderProcessor;
    }

    /**
     * Applies the given order list by validating and processing it.
     *
     * @param orderList the {@link OrderList} to be validated and processed.
     * @return a {@link Receipt} representing the result of processing the order.
     * @throws IllegalArgumentException if the order list is invalid.
     */
    public Receipt applyOrder(OrderList orderList) {
        validator.validate(orderList);

        return orderProcessor.processOrder(orderList);
    }
}
