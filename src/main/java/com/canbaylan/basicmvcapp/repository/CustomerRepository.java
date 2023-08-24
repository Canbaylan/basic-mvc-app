package com.canbaylan.basicmvcapp.repository;

import com.canbaylan.basicmvcapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :value, '%'))")
    List<Customer> findCustomersByName(String value);
}
