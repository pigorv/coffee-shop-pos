package org.example.service.extractor;

import org.example.model.Extra;
import org.example.model.OrderList;
import org.example.model.Product;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The {@code DefaultOrderListExtractor} class implements the {@link OrderListExtractor} interface
 * and provides functionality to parse a string representation of an order list into an {@link OrderList}.
 * The order list string should contain products separated by commas, with optional extras
 * specified using the "with" keyword and further extras separated by "and".
 */
public class DefaultOrderListExtractor implements OrderListExtractor {

    private static final String PRODUCTS_SEPARATOR = ",";
    private static final String PRODUCT_EXTRAS_SEPARATOR = "with";
    private static final String EXTRAS_SEPARATOR = "and";


    /**
     * Extracts an {@link OrderList} from a string representation of an order.
     *
     * @param orderAsString the string representation of the order list. Each product is separated by a comma,
     *                      and extras for each product are specified after "with" and separated by "and".
     * @return the {@link OrderList} parsed from the provided string.
     * @throws OrderListExtractorException if the input string is null, empty, or if there is an error during parsing.
     */
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
        if (extras.isEmpty() || extras.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        return Arrays.stream(extras.split(EXTRAS_SEPARATOR))
                .map(String::trim)
                .map(Extra::new)
                .toList();
    }
}
