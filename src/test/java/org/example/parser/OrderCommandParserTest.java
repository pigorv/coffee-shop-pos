package org.example.parser;

import org.example.service.parser.OrderCommandParser;
import org.example.service.parser.OrderCommandParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderCommandParserTest {
    private final OrderCommandParser parser = new OrderCommandParser();

    @Test
    void testValidOrderCommand() {
        String input = "--order=\"someOrder\"";
        String expected = "someOrder";
        String result = parser.parseCommandValue(input);
        assertEquals(expected, result);
    }

    @Test
    void testInvalidOrderCommandNoOrder() {
        String input = "--other=\"someValue\"";
        assertThrows(OrderCommandParsingException.class, () -> parser.parseCommandValue(input));
    }

    @Test
    void testInvalidOrderCommandMalformedOrder1() {
        String input = "--order=someOrder";
        assertThrows(OrderCommandParsingException.class, () -> parser.parseCommandValue(input));
    }

    @Test
    void testInvalidOrderCommandMalformedOrder2() {
        String input = "--order=\"someOrder";
        assertThrows(OrderCommandParsingException.class, () -> parser.parseCommandValue(input));
    }

    @Test
    void testEmptyInput() {
        String input = "";
        assertThrows(OrderCommandParsingException.class, () -> parser.parseCommandValue(input));
    }

    @Test
    void testNullInput() {
        String input = null;
        assertThrows(OrderCommandParsingException.class, () -> parser.parseCommandValue(input));
    }
}