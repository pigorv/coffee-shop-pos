package org.example.printer;

import org.example.model.ExtraItem;
import org.example.model.ProductItem;
import org.example.model.Receipt;
import org.example.model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StdoutReceiptPrinterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final StdoutRecieptPrinter printer = new StdoutRecieptPrinter();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testPrint_NoProducts() {
        // given
        List<ProductItem> products = List.of();
        Receipt receipt = new Receipt(products, BigDecimal.ZERO);

        // when
        printer.print(receipt);

        // then
        String expectedOutput = """
                Receipt:
                ----------------------------------------
                Total:                                   0.00""";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testPrint_WithProductsAndExtras() {
        // given
        List<ProductItem> products = List.of(
                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.valueOf(1.00), List.of()),
                new ProductItem("Bread", Type.SNACK, BigDecimal.valueOf(2.99), BigDecimal.ZERO, List.of()),
                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of(
                        new ExtraItem("Milk", BigDecimal.valueOf(0.50), BigDecimal.ZERO),
                        new ExtraItem("Sugar", BigDecimal.valueOf(0.25), BigDecimal.ZERO)
                ))
        );
        Receipt receipt = new Receipt(products, BigDecimal.valueOf(8.47));

        // when
        printer.print(receipt);

        // then
        String expectedOutput = """
                Receipt:
                Apple                                    1.49   (-1.00)   0.49
                Bread                                    2.99   2.99
                Coffee                                   4.99   4.99
                   Extras:
                        Milk                             0.50
                        Sugar                            0.25
                                                
                ----------------------------------------
                Total:                                   8.47""";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testPrint_WithProductsAndExtrasInAllProducts() {
        // given
        List<ProductItem> products = List.of(
                new ProductItem("Apple", Type.SNACK, BigDecimal.valueOf(1.49), BigDecimal.valueOf(1.00), List.of(
                        new ExtraItem("Milk", BigDecimal.valueOf(0.55), BigDecimal.ZERO)
                )),
                new ProductItem("Bread", Type.SNACK, BigDecimal.valueOf(2.99), BigDecimal.ZERO, List.of(
                        new ExtraItem("Cheese", BigDecimal.valueOf(1.20), BigDecimal.ZERO)

                )),
                new ProductItem("Coffee", Type.DRINK, BigDecimal.valueOf(4.99), BigDecimal.ZERO, List.of(
                        new ExtraItem("Milk", BigDecimal.valueOf(0.50), BigDecimal.ZERO),
                        new ExtraItem("Sugar", BigDecimal.valueOf(0.25), BigDecimal.ZERO)
                ))
        );
        Receipt receipt = new Receipt(products, BigDecimal.valueOf(8.47));

        // when
        printer.print(receipt);

        // then
        String expectedOutput = """
                Receipt:
                Apple                                    1.49   (-1.00)   0.49
                   Extras:
                        Milk                             0.55

                Bread                                    2.99   2.99
                   Extras:
                        Cheese                           1.20

                Coffee                                   4.99   4.99
                   Extras:
                        Milk                             0.50
                        Sugar                            0.25
                                                
                ----------------------------------------
                Total:                                   8.47""";
        assertEquals(expectedOutput, outContent.toString());
    }
}