package com.canbaylan.basicmvcapp.model;

import java.util.List;

public class Common {
    private Customer customer;
    private List<Order> orders;

    public Common(Customer customer, List<Order> orders) {
        this.customer = customer;
        this.orders = orders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
