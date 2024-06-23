package org.example.service.processor;

import org.example.repository.InMemoryExtraRepository;
import org.example.repository.InMemoryProductRepository;
import org.example.service.validator.OrderListValidator;

/**
 * The {@code OrderManagerFactory} class provides a factory method to create instances of {@link OrderManager}.
 * It sets up the necessary repositories, processors, and validators to ensure that the {@code OrderManager}
 * is properly configured.
 */
public class OrderManagerFactory {

    /**
     * Creates and returns a new instance of {@link OrderManager}.
     * This method initializes in-memory repositories for products and extras, sets up a base order processor,
     * and constructs a chain of post-order processors to handle additional processing steps.
     *
     * @return a fully configured {@link OrderManager} instance.
     */
    public static OrderManager createOrderManager() {
        // initializing repositories
        var productRepository = new InMemoryProductRepository();
        var extraRepository = new InMemoryExtraRepository();

        // creating base order processor
        var baseOrderProcessor = new ProductsAndExtrasBaseOrderProcessor(productRepository, extraRepository);

        // creating post order processor chain
        OrderProcessor orderProcessor =
                new TotalCountPostOrderProcessor(
                        new FreeExtraPostOrderProcessor(
                                new FreeBeveragePostOrderProcessor(
                                        baseOrderProcessor)));

        return new OrderManager(
                new OrderListValidator(productRepository, extraRepository),
                orderProcessor);
    }
}
