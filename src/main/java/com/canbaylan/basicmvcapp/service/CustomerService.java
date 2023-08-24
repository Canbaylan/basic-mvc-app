package com.canbaylan.basicmvcapp.service;

import com.canbaylan.basicmvcapp.model.Customer;
import com.canbaylan.basicmvcapp.model.Order;
import com.canbaylan.basicmvcapp.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<List<Customer>> getCustomers() {
        try{
            return new ResponseEntity<List<Customer>>(customerRepository.findAll(), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Customer>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> addCustomer(Map<String, String> requestMap) {
        try {
            Customer customer = new Customer();
            customer.setName(requestMap.get("name"));
            customer.setAge(Integer.parseInt(requestMap.get("age")));
            customerRepository.save(customer);
            return ResponseEntity.ok("Customer added successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong.");
        }
    }

    public ResponseEntity<String> updateCustomer(Map<String, String> requestMap) {
        try {
            Integer customerId = Integer.parseInt(requestMap.get("customerId"));
            Customer customer = customerRepository.findById(customerId)
                    .orElse(null);
            if (customer != null) {
                customer.setId(Integer.parseInt(requestMap.get("customerId")));
                customer.setName(requestMap.get("name"));
                customer.setAge(Integer.parseInt(requestMap.get("age")));
                customerRepository.save(customer);

                return ResponseEntity.ok("Customer updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Customer not found.");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong.");
        }
    }

    public ResponseEntity<String> deleteCustomer(Map<String, String> requestMap) {
        try {
            Integer customerId = Integer.parseInt(requestMap.get("customerId"));
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                customerRepository.delete(customer.get());
                return ResponseEntity.ok("Customer deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Customer not found.");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong.");
        }
    }
}
