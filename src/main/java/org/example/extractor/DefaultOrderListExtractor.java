package org.example.extractor;

import org.example.model.Extra;
import org.example.model.OrderList;
import org.example.model.Product;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultOrderListExtractor implements OrderListExtractor {

    private static final String PRODUCTS_SEPARATOR = ",";
    private static final String PRODUCT_EXTRAS_SEPARATOR = "with";
    private static final String EXTRAS_SEPARATOR = "and";

    @Override
    public OrderList extractOrderList(String orderAsString) {
        if (orderAsString == null || orderAsString.isEmpty()) {
            throw new OrderListExtractorException(orderAsString);
        }

        try {
            var products = Arrays.stream(orderAsString.split(PRODUCTS_SEPARATOR))
                    .map(this::extractProductWithExtras)
                    .toList();
            return new OrderList(products);
        } catch (Exception ex) {
            throw new OrderListExtractorException(orderAsString);
        }
    }

    private Product extractProductWithExtras(String product) {
        String[] productAndExtras = product.split(PRODUCT_EXTRAS_SEPARATOR);
        String productName = productAndExtras[0].trim();

        List<Extra> extras = Collections.emptyList();
        if (productAndExtras.length > 1) {
            extras = extractExtras(productAndExtras[1]);
        } else if (product.contains(PRODUCT_EXTRAS_SEPARATOR)) {
            throw new IllegalArgumentException("Expected extra");
        }

        return new Product(productName, extras);
    }

    private List<Extra> extractExtras(String extras) {
        if (extras.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return Arrays.stream(extras.split(EXTRAS_SEPARATOR))
                .map(String::trim)
                .map(Extra::new)
                .toList();
    }
}
