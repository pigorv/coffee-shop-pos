package org.example.processor;

import org.example.model.Extra;
import org.example.model.OrderList;
import org.example.model.Product;
import org.example.repository.ExtraRepository;
import org.example.repository.ProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderListValidator implements Validator<OrderList> {
    private final ProductRepository productRepository;
    private final ExtraRepository extraRepository;

    public OrderListValidator(ProductRepository productRepository, ExtraRepository extraRepository) {
        this.productRepository = productRepository;
        this.extraRepository = extraRepository;
    }

    @Override
    public void validate(OrderList orderList) {
        Objects.requireNonNull(orderList);

        List<Product> products = orderList.products();
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order must have products");
        }

        validateProducts(products);
        validateExtras(products);
    }

    private void validateProducts(List<Product> products) {
        // todo: can be optimized to not load each item 1-by-1
        Optional<String> firstNotExistedProduct = products.stream().map(Product::name).distinct()
                .filter(product -> productRepository.findByName(product) == null)
                .findFirst();
        if (firstNotExistedProduct.isPresent()) {
            throw new IllegalArgumentException("There is no such product as " +
                                               firstNotExistedProduct.get());
        }
    }

    private void validateExtras(List<Product> products) {
        // todo: can be optimized to not load each item 1-by-1
        Optional<String> firstNotExistedExtra = products.stream()
                .flatMap(product -> product.extras().stream())
                .map(Extra::name)
                .distinct()
                .filter(extra -> extraRepository.findByName(extra) == null)
                .findFirst();
        if (firstNotExistedExtra.isPresent()) {
            throw new IllegalArgumentException("There is no such extra as " +
                                               firstNotExistedExtra.get());
        }
    }
}