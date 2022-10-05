package vlad.skiba.javacore.consoleshop.helper.impl;

import vlad.skiba.javacore.consoleshop.exception.OrderException;
import vlad.skiba.javacore.consoleshop.order.Order;
import vlad.skiba.javacore.consoleshop.order.OrdersCompletionRunnable;
import vlad.skiba.javacore.consoleshop.order.OrdersPerformingRunnable;
import vlad.skiba.javacore.consoleshop.service.OrderService;
import vlad.skiba.javacore.consoleshop.helper.OrdersMenuDisplayHelper;

import java.util.Scanner;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class OrdersMenuDisplayHelperImpl implements OrdersMenuDisplayHelper {

    private static String separator = "-----------------------------------------------------------------------------\n";
    private static final String MENU = separator + "Orders menu: \n" + separator + "1. Create order \n2. Display orders" +
            "\n3. Start to work with orders \n4. Stop to work with orders and to exit from orders menu ";
    private static final Scanner USER_INPUT_SCANNER = new Scanner(System.in);
    private final OrderService orderService;
    private Thread ordersPerformerThread;
    private Thread ordersExecutorThread;

    public OrdersMenuDisplayHelperImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void displayOrdersMenu() {
        try {
            boolean isContinue = true;
            while (isContinue) {
                System.out.println(MENU);
                System.out.print(separator + "Enter orders menu item: \n" + separator);
                int choice = USER_INPUT_SCANNER.nextInt();
                try {
                    isContinue = handleChoice(choice);
                } catch (OrderException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } finally {
            stopToWorkWithOrders();
        }
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                handleCreateOrder();
                return true;
            case 2:
                displayOrders();
                return true;
            case 3:
                stopToWorkWithOrders();
                startToWorkWithOrders();
                return true;
            case 4:
                return false;
            default:
                throw new OrderException("Incorrect input... Reenter orders menu item! ");
        }
    }

    private void handleCreateOrder() {
        System.out.println("Enter the name of the product for which you want to create an order: ");
        String productName = USER_INPUT_SCANNER.next();
        orderService.createOrder(productName);
    }

    private void displayOrders() {
        StringBuilder message = new StringBuilder();
        for (Order order : orderService.getOrders()) {
            message.append(order.toString());
        }
        System.out.printf(separator + "List of orders: \n" + separator + "\n%s", message);
    }

    private void startToWorkWithOrders() {
        ordersPerformerThread = new Thread(new OrdersPerformingRunnable(orderService));
        ordersExecutorThread = new Thread(new OrdersCompletionRunnable(orderService));
        ordersPerformerThread.start();
        ordersExecutorThread.start();
    }

    private void stopToWorkWithOrders() {
        if (ordersPerformerThread != null && !ordersPerformerThread.isInterrupted()) {
            ordersPerformerThread.interrupt();
        }
        if (ordersExecutorThread != null && !ordersExecutorThread.isInterrupted()) {
            ordersExecutorThread.interrupt();
        }
    }

}