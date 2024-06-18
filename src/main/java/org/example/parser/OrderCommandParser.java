package org.example.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderCommandParser implements CommandParser {
    private static final Pattern ORDER_COMMAND_PATTERN = Pattern.compile("--order=\"([^\"]*)\"");
    @Override
    public String parseCommandValue(String input) {
        return Optional.ofNullable(input)
                .map(ORDER_COMMAND_PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1))
                .orElseThrow(() -> new OrderCommandParsingException(input));
    }
}
