package org.example.processor;

import org.example.repository.InMemoryExtraRepository;
import org.example.repository.InMemoryProductRepository;

public class OrderManagerFactory {
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
