package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationIntegrationTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testApp() {
        CoffeeShopPOSApplicationStarter.main(
                "--order=\"large coffee, bacon roll\"".split(" ")
        );

        String expectedOutput = "Receipt:\n" +
                                "large coffee                             3.55   3.55\n" +
                                "bacon roll                               4.53   4.53\n" +
                                "----------------------------------------\n" +
                                "Total:                                   8.08";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAppWithBonusProgram() {
        CoffeeShopPOSApplicationStarter.main(
                "--order=\"large coffee with extra milk, bacon roll\"".split(" ")
        );

        String expectedOutput = "Receipt:\n" +
                                "large coffee                             3.55   3.55\n" +
                                "   Extras: \n" +
                                "        extra milk                       0.32\n" +
                                "\n" +
                                "bacon roll                               4.53   4.53\n" +
                                "----------------------------------------\n" +
                                "Total:                                   8.08";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAppWithBonusProgramNthBeverage() {
        CoffeeShopPOSApplicationStarter.main(
                "--order=\"large coffee with extra milk, small coffee, large coffee, medium coffee, freshly squeezed orange juice, bacon roll\"".split(" ")
        );

        String expectedOutput = "Receipt:\n" +
                                "large coffee                             3.55   3.55\n" +
                                "   Extras: \n" +
                                "        extra milk                       0.32\n" +
                                "\n" +
                                "small coffee                             2.55   2.55\n" +
                                "large coffee                             3.55   3.55\n" +
                                "medium coffee                            3.05   3.05\n" +
                                "freshly squeezed orange juice            4.53   (-4.53)   0.00\n" +
                                "bacon roll                               4.53   4.53\n" +
                                "----------------------------------------\n" +
                                "Total:                                   17.23";
        assertEquals(expectedOutput, outContent.toString());
    }
}
