package service;

import model.Order;
import util.FileHandler;

import java.util.*;

public class OrderService {

    private Queue<Order> orderQueue = new LinkedList<>();

    // Method to place order
    public void placeOrder(String customerUsername, int restaurantId,List<String> foodList, String timestamp,double totalAmount) {
        int orderId = generateOrderId(); // Unique order ID
        Order newOrder = new Order(orderId, customerUsername,restaurantId, foodList, timestamp,totalAmount);

        // Adding order to queue
        orderQueue.add(newOrder);

        FileHandler.saveOrder(newOrder);

        System.out.println("Order placed successfully! Order ID: " + orderId);
    }

    private int generateOrderId() {
        return (int) (System.currentTimeMillis() % 10000);
    }

    // Method to process order (FIFO)
    public void processOrder() {
        if (!orderQueue.isEmpty()) {
            Order orderToProcess = orderQueue.poll(); // FIFO - First In First Out
            System.out.println("Processing Order: ");
            orderToProcess.displayOrder();
        } else {
            System.out.println("No orders to process.");
        }
    }

    // Method to view all orders (for restaurant)
    public void viewOrders() {
        if (!orderQueue.isEmpty()) {
            for (Order order : orderQueue) {
                order.displayOrder();
            }
        } else {
            System.out.println("No orders available.");
        }
    }

    // View orders placed by a specific restaurant
    public void viewOrdersByRestaurant(int restaurantId) {
        List<String> orders = FileHandler.readFile("data/orders.txt");
        for (String orderLine : orders) {
            String[] orderData = orderLine.split(",");
            if (Integer.parseInt(orderData[2]) == restaurantId) {
                System.out.println("Order ID: " + orderData[0] +
                        ", Customer: " + orderData[1] +
                        ", Timestamp: " + orderData[3] +
                        ", Order Details: " + orderData[4]);
            }
        }
    }

    // Confirm order (for restaurant to mark the order as prepared)
    public void confirmOrder(int orderId) {
        List<String> orders = FileHandler.readFile("data/orders.txt");
        for (int i = 0; i < orders.size(); i++) {
            String[] orderData = orders.get(i).split(",");
            if (Integer.parseInt(orderData[0]) == orderId) {
                orderData[4] = "Confirmed"; // Mark as confirmed (order prepared)
                orders.set(i, String.join(",", orderData));
                FileHandler.overwriteFile("data/orders.txt", orders);
                System.out.println("Order " + orderId + " confirmed!");
                return;
            }
        }
        System.out.println("Order not found!");
    }
}
