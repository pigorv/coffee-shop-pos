package org.example.parser;

public class OrderCommandParsingException extends RuntimeException {
    private static final String ORDER_COMMAND_EXCEPTED_FORMAT =
            "Command expected to be in the following format: --order=\"text\"";
    private static final String ORDER_COMMAND_EXCEPTION_MESSAGE =
            "%s is a wrong command. " + ORDER_COMMAND_EXCEPTED_FORMAT;

    public OrderCommandParsingException(String input) {
        super(String.format(ORDER_COMMAND_EXCEPTION_MESSAGE, input));
    }
}
