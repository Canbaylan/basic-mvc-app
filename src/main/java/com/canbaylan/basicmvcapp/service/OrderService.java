package com.canbaylan.basicmvcapp.service;

import com.canbaylan.basicmvcapp.model.Customer;
import com.canbaylan.basicmvcapp.model.Order;
import com.canbaylan.basicmvcapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ResponseEntity<List<Order>> getOrders() {
        try {
            return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> addOrder(Map<String, String> requestMap) {
        try {
            Order order = new Order();
            order.setTotalPrice(Double.parseDouble(requestMap.get("totalPrice")));

            Customer customer = new Customer();
            customer.setId(Integer.parseInt(requestMap.get("customerId")));
            order.setCustomer(customer);
            orderRepository.save(order);
            return ResponseEntity.ok("Order added successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong.");
        }
    }

    public ResponseEntity<String> updateOrder(Map<String, String> requestMap) {
        try {
            Integer orderId = Integer.parseInt(requestMap.get("orderId"));
            Order order = orderRepository.findById(orderId)
                    .orElse(null);
            if (order != null) {
                order.setId(Integer.parseInt(requestMap.get("orderId")));
                order.setTotalPrice(Double.parseDouble(requestMap.get("totalPrice")));
                orderRepository.save(order);
                return ResponseEntity.ok("Order updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong.");
        }
    }

    public ResponseEntity<String> deleteOrder(Map<String, String> requestMap) {
        try {
            Integer orderId = Integer.parseInt(requestMap.get("orderId"));
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                orderRepository.delete(order.get());
                return ResponseEntity.ok("Order deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong.");
        }
    }

    public ResponseEntity<List<Order>> getOrdersAfterDate(Date date) {
        try {
            List<Order> orders = this.OrdersAfterDate(date);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Order> OrdersAfterDate(Date date) {
        try {
            List<Order> orders = entityManager.createQuery(
                            "SELECT o FROM Order o WHERE o.createDate > :date", Order.class)
                    .setParameter("date", date)
                    .getResultList();

            return orders;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
