package org.example.processor;

import org.example.model.OrderList;
import org.example.model.ProductItem;
import org.example.model.Receipt;

import java.math.BigDecimal;

public class TotalCountPostOrderProcessor extends PostOrderProcessor {
    public TotalCountPostOrderProcessor(OrderProcessor delegate) {
        super(delegate);
    }

    @Override
    public Receipt processOrder(OrderList order) {
        Receipt receipt = super.delegate.processOrder(order);

        BigDecimal totalPrice = receipt.products().stream()
                .map(this::calculatePriceForProduct)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        return new Receipt(receipt.products(), totalPrice);
    }

    private BigDecimal calculatePriceForProduct(ProductItem product) {
        return product.price()
                .subtract(product.discount())
                .add(calculateTotalPriceForExtras(product));
    }

    private BigDecimal calculateTotalPriceForExtras(ProductItem product) {
        return product.extras().stream()
                .map(extra -> extra.price().subtract(extra.discount()))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
