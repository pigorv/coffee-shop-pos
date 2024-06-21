package org.example.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code OrderCommandParser} class implements the {@link CommandParser} interface and provides
 * functionality to parse the value of an order command from a given input string.
 * The input string is expected to contain an order command in the format: {@code --order="order details"}.
 */
public class OrderCommandParser implements CommandParser {
    private static final Pattern ORDER_COMMAND_PATTERN = Pattern.compile("--order=\"([^\"]*)\"");

    /**
     * Parses the value of the order command from the provided input string.
     * The method searches for a pattern in the format of {@code --order="order details"} and returns the
     * order details if found.
     *
     * @param input the input string containing the order command.
     * @return the extracted order details as a string.
     * @throws OrderCommandParsingException if the input string is null, does not contain a valid order command,
     *                                      or if the order command is improperly formatted.
     */
    @Override
    public String parseCommandValue(String input) {
        return Optional.ofNullable(input)
                .map(ORDER_COMMAND_PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1))
                .orElseThrow(() -> new OrderCommandParsingException(input));
    }
}
