package com.canbaylan.basicmvcapp.service;

import com.canbaylan.basicmvcapp.model.Common;
import com.canbaylan.basicmvcapp.model.Customer;
import com.canbaylan.basicmvcapp.model.Order;
import com.canbaylan.basicmvcapp.repository.CustomerRepository;
import com.canbaylan.basicmvcapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<List<Common>> getCustomerOrdersContaining(String value) {
        List<Customer> customers = customerRepository.findCustomersByName(value);
        List<Common> customerOrderInfoList = new ArrayList<>();

        for (Customer customer : customers) {
            List<Order> orders = orderRepository.findByCustomer(customer);
            Common commonInfo = new Common(customer, orders);
            customerOrderInfoList.add(commonInfo);
        }

        return ResponseEntity.ok(customerOrderInfoList);
    }
    public ResponseEntity<List<Customer>> getCustomersWithoutOrders() {
        try {
            List<Customer> customersWithoutOrders = new ArrayList<>();
            List<Customer> allCustomers = customerRepository.findAll();

            for (Customer customer : allCustomers) {
                List<Order> orders = orderRepository.findByCustomer(customer);
                if (orders.isEmpty()) {
                    customersWithoutOrders.add(customer);
                }
            }

            return new ResponseEntity<>(customersWithoutOrders, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
