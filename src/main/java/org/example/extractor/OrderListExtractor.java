package org.example.extractor;

import org.example.model.OrderList;

public interface OrderListExtractor {
    OrderList extractOrderList(String orderAsString);
}
