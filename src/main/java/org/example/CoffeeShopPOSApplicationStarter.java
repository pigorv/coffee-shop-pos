package org.example;

import org.example.extractor.DefaultOrderListExtractor;
import org.example.extractor.OrderListExtractor;
import org.example.parser.CommandParser;
import org.example.parser.OrderCommandParser;
import org.example.printer.ReceiptPrinter;
import org.example.printer.StdoutReceiptPrinter;
import org.example.processor.OrderManager;
import org.example.processor.OrderManagerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code CoffeeShopPOSApplicationStarter} class initializes and starts the Coffee Shop POS application.
 * It parses command-line arguments to process orders and prints receipts using various components.
 * <p>
 * The application flow includes:
 * <ul>
 *     <li>Command parsing using {@link OrderCommandParser}</li>
 *     <li>Order list extraction using {@link DefaultOrderListExtractor}</li>
 *     <li>Order management using {@link OrderManagerFactory}</li>
 *     <li>Receipt printing using {@link StdoutReceiptPrinter}</li>
 * </ul>
 */
public class CoffeeShopPOSApplicationStarter {
    private static final Logger LOGGER = Logger.getLogger(CoffeeShopPOSApplicationStarter.class.getName());

    /**
     * Main entry point of the Coffee Shop POS application.
     * Processes command-line arguments to parse orders, manage orders, and print receipts.
     *
     * @param args the command-line arguments containing order details
     */
    public static void main(String[] args) {
        try {
            CommandParser commandParser = new OrderCommandParser();
            OrderListExtractor orderListExtractor = new DefaultOrderListExtractor();
            OrderManager orderManager = OrderManagerFactory.createOrderManager();
            ReceiptPrinter printer = new StdoutReceiptPrinter();

            printer.print(
                    orderManager.applyOrder(orderListExtractor.extractOrderList(
                            commandParser.parseCommandValue(
                                    String.join(" ",args)))));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Application failed during processing: " + ex.getMessage(), ex);
        }
    }
}
