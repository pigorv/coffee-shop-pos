package org.example;

import org.example.extractor.DefaultOrderListExtractor;
import org.example.extractor.OrderListExtractor;
import org.example.parser.CommandParser;
import org.example.parser.OrderCommandParser;
import org.example.printer.ReceiptPrinter;
import org.example.printer.StdoutRecieptPrinter;
import org.example.processor.OrderManager;
import org.example.processor.OrderManagerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CoffeeShopPOSApplicationStarter {
    private static final Logger LOGGER = Logger.getLogger(CoffeeShopPOSApplicationStarter.class.getName());

    public static void main(String[] args) {
        try {
            CommandParser commandParser = new OrderCommandParser();
            OrderListExtractor orderListExtractor = new DefaultOrderListExtractor();
            OrderManager orderManager = OrderManagerFactory.createOrderManager();
            ReceiptPrinter printer = new StdoutRecieptPrinter();

            printer.print(
                    orderManager.applyOrder(orderListExtractor.extractOrderList(
                            commandParser.parseCommandValue(
                                    String.join(" ",args)))));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Application failed during processing: " + ex.getMessage(), ex);
        }
    }
}
