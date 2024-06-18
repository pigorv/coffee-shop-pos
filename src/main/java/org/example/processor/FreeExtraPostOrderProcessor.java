package org.example.processor;

import org.example.model.ExtraItem;
import org.example.model.OrderList;
import org.example.model.ProductItem;
import org.example.model.Receipt;

import java.util.ArrayList;

public class FreeExtraPostOrderProcessor extends PostOrderProcessor {
    public FreeExtraPostOrderProcessor(OrderProcessor delegate) {
        super(delegate);
    }

    @Override
    public Receipt processOrder(OrderList order) {
        Receipt receipt = super.delegate.processOrder(order);

        if (receipt.products().isEmpty()) {
            return receipt;
        }

        int beverages = 0;
        int snacks = 0;
        for (ProductItem product : receipt.products()) {
            switch (product.type()) {
                case SNACK -> snacks++;
                case DRINK -> beverages++;
            }
        }

        var products = new ArrayList<ProductItem>();
        for (ProductItem product : receipt.products()) {
            var extras = new ArrayList<ExtraItem>();
            for (ExtraItem extra : product.extras()) {
                if (beverages >= 1 && snacks >= 1) {
                    extras.add(new ExtraItem(extra.name(), extra.price(), extra.price()));
                    beverages--;
                    snacks--;
                } else {
                    extras.add(extra);
                }
            }
            products.add(new ProductItem(product.name(),
                    product.type(),
                    product.price(),
                    product.discount(),
                    extras));
        }

        return new Receipt(products, receipt.total());
    }
}
