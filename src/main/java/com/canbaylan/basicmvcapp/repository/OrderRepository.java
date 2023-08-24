package com.canbaylan.basicmvcapp.repository;

import com.canbaylan.basicmvcapp.model.Customer;
import com.canbaylan.basicmvcapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.customer = :customer")
    List<Order> findByCustomer(Customer customer);
}
