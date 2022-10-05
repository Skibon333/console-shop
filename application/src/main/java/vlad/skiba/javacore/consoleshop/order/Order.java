package vlad.skiba.javacore.consoleshop.order;

import vlad.skiba.javacore.consoleshop.model.Product;

import java.time.Duration;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class Order {

    private Long id;
    private Product product;
    private Status status = Status.FRAMED;
    private Duration startExecutionTime;
    private Duration executingTime;

    public Order(Long id, Product product) {
        this.id = id;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Duration getStartExecutionTime() {
        return startExecutionTime;
    }

    public void setStartExecutionTime(Duration startExecutionTime) {
        this.startExecutionTime = startExecutionTime;
    }

    public Duration getExecutingTime() {
        return executingTime;
    }

    public void setExecutingTime(Duration executingTime) {
        this.executingTime = executingTime;
    }

    @Override
    public String toString() {
        return String.format("Id: %d \nProduct name: %s \nStatus: %s\n\n", getId(), product.getName(), getStatus());
    }

}