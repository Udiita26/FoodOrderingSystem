package model;

import java.util.List;

public class Order {
    private int orderId;
    private String customerUsername;
    private int restaurantId;
    private List<String> foodList; // FoodName|Qty
    private String timestamp;
    public double totalAmount;

    public Order(int orderId, String customerUsername, int restaurantId, List<String> foodList, String timestamp , double totalAmount) {
        this.orderId = orderId;
        this.customerUsername = customerUsername;
        this.restaurantId = restaurantId;
        this.foodList = foodList;
        this.timestamp = timestamp;
        this.totalAmount = totalAmount;

    }

    // Getters
    public int getOrderId() { return orderId; }
    public String getCustomerUsername() { return customerUsername; }
    public int getRestaurantId() { return restaurantId; }
    public List<String> getFoodList() { return foodList; }
    public String getTimestamp() { return timestamp; }
    public double getTotalAmount() {
        return totalAmount;
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customerUsername);
        System.out.println("Items: " + foodList);
        System.out.println("Total: ₹" + totalAmount);
    }
}

