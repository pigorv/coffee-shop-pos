package org.example.processor;

import org.example.model.OrderList;
import org.example.model.Receipt;

public class OrderManager {
    private final OrderListValidator validator;
    private final OrderProcessor orderProcessor;

    public OrderManager(OrderListValidator validator, OrderProcessor orderProcessor) {
        this.validator = validator;
        this.orderProcessor = orderProcessor;
    }

    public Receipt applyOrder(OrderList orderList) {
        validator.validate(orderList);

        return orderProcessor.processOrder(orderList);
    }
}
