package vlad.skiba.javacore.consoleshop.service;

import vlad.skiba.javacore.consoleshop.order.Order;

import java.util.List;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public interface OrderService {

    List<Order> getOrders();

    void createOrder(String productName);

    void performFramedOrders();

    void completePerformedOrders();

}