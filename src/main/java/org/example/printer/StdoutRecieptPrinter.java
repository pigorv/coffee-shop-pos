package org.example.printer;

import org.example.model.ExtraItem;
import org.example.model.ProductItem;
import org.example.model.Receipt;

import java.math.BigDecimal;
import java.util.List;

public class StdoutRecieptPrinter implements ReceiptPrinter {
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
            System.out.println("   Extras: ");
            for (ExtraItem extra : extras) {
                System.out.printf("        %-32s %.2f%n", extra.name(), extra.price());
            }
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
