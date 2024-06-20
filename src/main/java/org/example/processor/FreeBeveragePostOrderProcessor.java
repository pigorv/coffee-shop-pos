package org.example.processor;

import org.example.model.OrderList;
import org.example.model.ProductItem;
import org.example.model.Receipt;
import org.example.model.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FreeBeveragePostOrderProcessor extends PostOrderProcessor {
    private static final int N_FREE_BEVERAGE = 5;

    public FreeBeveragePostOrderProcessor(OrderProcessor delegate) {
        super(delegate);
    }

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
                products.add(new ProductItem(productItem.name(),
                        productItem.type(),
                        productItem.price(),
                        // set 100% discount
                        productItem.price(),
                        productItem.extras()));
                count = 0;
            } else {
                products.add(productItem);
            }
        }
        return products;
    }
}
