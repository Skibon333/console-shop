package vlad.skiba.javacore.consoleshop.service.impl;

import vlad.skiba.javacore.consoleshop.exception.OrderException;
import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.order.Order;
import vlad.skiba.javacore.consoleshop.order.Status;
import vlad.skiba.javacore.consoleshop.service.CategoryService;
import vlad.skiba.javacore.consoleshop.service.OrderService;

import java.util.stream.Collectors;
import java.time.Duration;
import java.util.*;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class OrderServiceImpl implements OrderService {

    private static final Long MAX_EXECUTING_TIME_IN_SECONDS = 60L;
    private static final Long MIN_EXECUTING_TIME_IN_SECONDS = 1L;
    private static final Random ORDER_EXECUTING_TIME_GENERATOR = new Random();
    private static final Object ORDERS_BLOCKER = new Object();
    private List<Order> orders = new ArrayList<>();
    private final CategoryService categoryService;
    private static Long id = 0L;

    public OrderServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public void createOrder(String productName) {
        findProductByName(productName).ifPresentOrElse(
                this::addOrder,
                () -> {
                    throw new OrderException("Product doesn't exists. Please, add it first! ");
                }
        );
        System.out.printf("Order on product \"%s\" was successfully created and added to orders list! \n", productName);
    }

    @Override
    public void performFramedOrders() {
        synchronized (ORDERS_BLOCKER) {
            for (Order order : findFramedOrders()) {
                order.setStatus(Status.PERFORMED);
                order.setStartExecutionTime(Duration.ofSeconds(getCurrentTimeInSeconds()));
                order.setExecutingTime(Duration.ofSeconds(ORDER_EXECUTING_TIME_GENERATOR.nextInt(
                        MAX_EXECUTING_TIME_IN_SECONDS.intValue()) + MIN_EXECUTING_TIME_IN_SECONDS));
                System.out.printf("Status of order №%d changed to \"PERFORMED\"\n", order.getId());
            }
        }
    }

    @Override
    public void completePerformedOrders() {
        synchronized (ORDERS_BLOCKER) {
            for (Order order : findPerformedOrders()) {
                boolean isOrderCompleted = Duration.ofSeconds(getCurrentTimeInSeconds())
                        .minus(order.getStartExecutionTime()).minus(order.getExecutingTime()).isNegative();
                if (isOrderCompleted) {
                    order.setStatus(Status.COMPLETED);
                    System.out.printf("Status of order №%d changed to \"COMPLETED\"\n", order.getId());
                }
            }
        }
    }

    private Long getCurrentTimeInSeconds() {
        return System.currentTimeMillis() / 1000L;
    }

    private Optional<Product> findProductByName(String productName) {
        return categoryService.findAll()
                .stream()
                .map(Category::getProducts)
                .flatMap(Collection::stream)
                .filter(product -> product.getName().equals(productName))
                .findFirst();
    }

    private void addOrder(Product product) {
        synchronized (ORDERS_BLOCKER) {
            orders.add(new Order(++id, product));
        }
    }

    private List<Order> findFramedOrders() {
        return orders.stream()
                .filter(order -> order.getStatus() == Status.FRAMED)
                .collect(Collectors.toList());
    }

    private List<Order> findPerformedOrders() {
        return orders.stream()
                .filter(order -> order.getStatus() == Status.PERFORMED)
                .collect(Collectors.toList());
    }

}