package com.canbaylan.basicmvcapp.controller;

import com.canbaylan.basicmvcapp.model.Common;
import com.canbaylan.basicmvcapp.model.Order;
import com.canbaylan.basicmvcapp.service.CommonService;
import com.canbaylan.basicmvcapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getOrders() {
        try {
            return orderService.getOrders();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody Map<String, String> requestMap) {
        try {
            return orderService.addOrder(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody Map<String, String> requestMap) {
        try {
            return orderService.updateOrder(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrder(@RequestBody Map<String, String> requestMap) {
        try {
            return orderService.deleteOrder(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getOrdersAfterDate")
    public ResponseEntity<List<Order>> getOrdersAfterDate(@RequestParam("date") String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);
            return orderService.getOrdersAfterDate(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/getCustomerOrdersByKeyword")
    public ResponseEntity<List<Common>> getCustomerOrdersByKeyword(@RequestParam("keyword") String keyword) {
        try {
            return commonService.getCustomerOrdersContaining(keyword);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
