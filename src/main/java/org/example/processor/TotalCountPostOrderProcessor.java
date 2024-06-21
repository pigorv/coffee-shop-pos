package org.example.processor;

import org.example.model.OrderList;
import org.example.model.ProductItem;
import org.example.model.Receipt;

import java.math.BigDecimal;

/**
 * The {@code TotalCountPostOrderProcessor} class extends the {@link PostOrderProcessor} class
 * and provides additional functionality to calculate the total price of an order.
 * It calculates the total price by summing the prices of all products and their extras,
 * taking into account any discounts.
 */
public class TotalCountPostOrderProcessor extends PostOrderProcessor {
    public TotalCountPostOrderProcessor(OrderProcessor delegate) {
        super(delegate);
    }

    /**
     * Processes the provided {@link OrderList} and generates a {@link Receipt}.
     * This method first delegates the order processing to the underlying {@link OrderProcessor}
     * and then calculates the total price of the order by summing the prices of all products
     * and their extras, considering any discounts.
     *
     * @param order the {@link OrderList} to be processed.
     * @return a {@link Receipt} containing the processed product items and the calculated total price.
     */
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
