package org.example.processor;

public abstract class PostOrderProcessor implements OrderProcessor {
    protected final OrderProcessor delegate;

    protected PostOrderProcessor(OrderProcessor delegate) {
        this.delegate = delegate;
    }
}
