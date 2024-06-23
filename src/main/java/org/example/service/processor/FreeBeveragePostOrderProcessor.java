package org.example.service.processor;

import org.example.model.ExtraItem;
import org.example.model.OrderList;
import org.example.model.ProductItem;
import org.example.model.Receipt;
import org.example.model.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FreeBeveragePostOrderProcessor} class extends the {@link PostOrderProcessor} class and
 * provides additional functionality to apply discounts to beverages in an order.
 * It provides a free beverage for every set number of beverages ordered.
 */
public class FreeBeveragePostOrderProcessor extends PostOrderProcessor {
    private static final int N_FREE_BEVERAGE = 5;

    public FreeBeveragePostOrderProcessor(OrderProcessor delegate) {
        super(delegate);
    }

    /**
     * Processes the provided {@link OrderList} and generates a {@link Receipt}.
     * This method first delegates the order processing to the underlying {@link OrderProcessor}
     * and then applies a discount to every fifth beverage in the order.
     *
     * @param order the {@link OrderList} to be processed.
     * @return a {@link Receipt} containing the processed product items and the applied beverage discounts.
     */
    @Override
    public Receipt processOrder(OrderList order) {
        Receipt receipt = super.delegate.processOrder(order);
        if (receipt.products().size() < N_FREE_BEVERAGE) {
            return receipt;
        }

        List<ProductItem> products = createNewProductsWithDiscount(receipt);
        return new Receipt(products, BigDecimal.ZERO);
    }

    private static List<ProductItem> createNewProductsWithDiscount(Receipt receipt) {
        int count = 0;
        var products = new ArrayList<ProductItem>();
        for (int i = 0; i < receipt.products().size(); i++) {
            ProductItem productItem = receipt.products().get(i);
            if (productItem.type() == Type.DRINK) {
                count++;
            }

            if (count != 0 && count % N_FREE_BEVERAGE == 0) {
                List<ExtraItem> extras = createNewExtrasWithDiscounts(productItem.extras());
                products.add(new ProductItem(productItem.name(),
                        productItem.type(),
                        productItem.price(),
                        // set 100% discount
                        productItem.price(),
                        extras));
                count = 0;
            } else {
                products.add(productItem);
            }
        }
        return products;
    }

    private static List<ExtraItem> createNewExtrasWithDiscounts(List<ExtraItem> extras) {
        return extras.stream()
                .map(e -> new ExtraItem(e.name(), e.price(), e.price()))
                .toList();
    }
}
