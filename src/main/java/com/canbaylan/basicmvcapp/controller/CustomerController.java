package com.canbaylan.basicmvcapp.controller;

import com.canbaylan.basicmvcapp.model.Customer;
import com.canbaylan.basicmvcapp.service.CommonService;
import com.canbaylan.basicmvcapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CommonService commonService;


    @GetMapping("/getAll")
    public ResponseEntity<List<Customer>> getCustomer() {
        try{
            return customerService.getCustomers();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody Map<String,String> requestMap){
        try{
            return customerService.addCustomer(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestBody Map<String,String> requestMap){
        try{
            return customerService.updateCustomer(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCustomer(@RequestBody Map<String,String> requestMap){
        try{
            return customerService.deleteCustomer(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/getCustomersWithoutOrders")
    public ResponseEntity<List<Customer>> getCustomersWithoutOrders() {
        try {
            return commonService.getCustomersWithoutOrders();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
