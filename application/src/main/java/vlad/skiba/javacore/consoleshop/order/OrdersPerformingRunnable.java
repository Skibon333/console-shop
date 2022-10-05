package vlad.skiba.javacore.consoleshop.order;

import vlad.skiba.javacore.consoleshop.service.OrderService;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class OrdersPerformingRunnable implements Runnable {

    private static final long SLEEPING_THREAD_TIME_IN_MILLISECONDS = 1000L;
    private final OrderService orderService;

    public OrdersPerformingRunnable(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run() {
        boolean isActive = true;
        while (isActive) {
            System.out.printf("%s: Started searching \"FRAMED\" orders! \n", Thread.currentThread().getName());
            orderService.performFramedOrders();
            System.out.printf("%s: Finished searching \"FRAMED\" orders! \n", Thread.currentThread().getName());
            try {
                Thread.sleep(SLEEPING_THREAD_TIME_IN_MILLISECONDS);
            } catch (InterruptedException ex) {
                isActive = disable();
            }
        }
    }

    private boolean disable() {
        return false;
    }

}