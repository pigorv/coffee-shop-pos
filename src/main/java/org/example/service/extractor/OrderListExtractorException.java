package org.example.service.extractor;

public class OrderListExtractorException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = """
            Error: %s is invalid order format."
            Format orders as:"
            - With extras: "product with extra1 and extra2"
            - Without extras: "product"
            Examples:
            - "large coffee with extra milk and sugar"
            - "espresso"
            - "orange juice with ice"
            - "black coffee"
            - "tea with lemon and honey"
            """;

    public OrderListExtractorException(String orderAsString) {
        super(String.format(EXCEPTION_MESSAGE, orderAsString));
    }
}
