package org.example.service.processor;

import org.example.model.OrderList;
import org.example.model.Receipt;

public interface OrderProcessor {
    Receipt processOrder(OrderList order);
}
