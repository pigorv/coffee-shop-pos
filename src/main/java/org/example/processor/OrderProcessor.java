package org.example.processor;

import org.example.model.OrderList;
import org.example.model.Receipt;

public interface OrderProcessor {
    Receipt processOrder(OrderList order);
}
