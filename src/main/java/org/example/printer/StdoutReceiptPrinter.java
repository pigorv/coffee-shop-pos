package org.example.printer;

import org.example.model.ExtraItem;
import org.example.model.ProductItem;
import org.example.model.Receipt;

import java.math.BigDecimal;
import java.util.List;

/**
 * The {@code StdoutReceiptPrinter} class implements the {@link ReceiptPrinter} interface
 * and provides functionality to print receipts to the standard output (stdout).
 */
public class StdoutReceiptPrinter implements ReceiptPrinter {

    /**
     * Prints the given receipt to the standard output.
     * This method formats and prints the receipt in the following structure:
     * <pre>
     * Receipt:
     * large coffee                             3.55   3.55
     *    Extras:
     *         extra milk                       0.32   (-0.32)   0.00
     *
     * bacon roll                               4.53   4.53
     * ----------------------------------------
     * Total:                                   7.76
     * </pre>
     *
     * @param receipt the Receipt object containing the items to print
     */
    @Override
    public void print(Receipt receipt) {
        printHeader();
        printProductItems(receipt.products());
        printSeparator();
        printTotal(receipt.total());
    }

    private void printHeader() {
        System.out.println("Receipt:");
    }

    private void printProductItems(List<ProductItem> productItems) {
        for (ProductItem item : productItems) {
            System.out.printf("%-40s %.2f", item.name(), item.price());
            if (item.discount().compareTo(BigDecimal.ZERO) != 0) {
                System.out.printf("   (-%.2f)", item.discount());
            }
            System.out.printf("   %.2f%n", item.price().subtract(item.discount()));
            printExtras(item.extras());
        }
    }

    private void printExtras(List<ExtraItem> extras) {
        if (!extras.isEmpty()) {
            System.out.println("   Extras:");
            extras.forEach(extra -> System.out.printf("        %-32s %.2f%n", extra.name(), extra.price()));
            System.out.println();
        }
    }

    private void printSeparator() {
        System.out.println("----------------------------------------");
    }

    private void printTotal(BigDecimal totalPrice) {
        System.out.printf("%-40s %.2f", "Total:", totalPrice);
    }
}
