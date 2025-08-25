package service;

import model.Customer;
import model.Order;
import model.Food;
import util.FileHandler;

import java.util.*;

public class CustomerService {

    // Customer registration
    public void registerCustomer(Scanner sc) {
        System.out.println("Enter Username:");
        String username = sc.nextLine();
        System.out.println("Enter Password:");
        String password = sc.nextLine();
        System.out.println("Enter Email:");
        String email = sc.nextLine();
        System.out.println("Enter Phone Number:");
        String phoneNumber = sc.nextLine();

        Customer customer = new Customer(username, password, email, phoneNumber);
        List<String> userData = new ArrayList<>();
        userData.add(customer.getUsername() + "," + customer.getPassword() + "," + customer.getRole());

        FileHandler.writeFile("data/users.txt", userData, true);
        System.out.println("Customer registered successfully!");
    }

    // Customer login
    public void loginCustomer(Scanner sc) {
        System.out.println("Enter Username:");
        String username = sc.nextLine();
        System.out.println("Enter Password:");
        String password = sc.nextLine();

        List<String> users = FileHandler.readFile("data/users.txt");
        for (String userLine : users) {
            String[] userData = userLine.split(",");
            if (userData[0].equals(username) && userData[1].equals(password)) {
                System.out.println("Login successful!");
                if (userData[2].equals("customer")) {
                    showCustomerMenu(sc, username);
                } else {
                    System.out.println("Access denied for admin user!");
                }
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }

    // Show menu for customers after login
    private void showCustomerMenu(Scanner sc, String username) {
        while (true) {
            System.out.println("\nCustomer Menu");
            System.out.println("1. View All Restaurants");
            System.out.println("2. Filter Restaurants by Food");
            System.out.println("3. Place Order");
            System.out.println("4. View Orders");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewRestaurants();
                case 2 -> filterRestaurantsByFood(sc);
                case 3 -> placeOrder(sc, username);
                case 4 -> viewOrders(username);
                case 5 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // View all restaurants
    private void viewRestaurants() {
        List<String> restaurants = FileHandler.readFile("data/restaurants.txt");
        for (String line : restaurants) {
            String[] data = line.split(",");
            System.out.println("ID: " + data[0] + ", Name: " + data[1] + ", Location: " + data[2]);
        }
    }

    // Filter restaurants by food item
    private void filterRestaurantsByFood(Scanner sc) {
        System.out.println("Enter Food Name to Search:");
        String foodName = sc.nextLine();
        List<String> foods = FileHandler.readFile("data/foods.txt");

        for (String foodLine : foods) {
            String[] data = foodLine.split(",");
            if (data[2].toLowerCase().contains(foodName.toLowerCase()) && Boolean.parseBoolean(data[4])) {
                System.out.println("Food: " + data[2] + " (ID: " + data[0] + "), Price: " + data[3]);
            }
        }
    }

    // Place an order
    private void placeOrder(Scanner sc, String username) {
        System.out.println("Enter Restaurant ID:");
        int restaurantId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.println("Enter Food ID:");
        int foodId = sc.nextInt();
        System.out.println("Enter Quantity:");
        int qty = sc.nextInt();
        sc.nextLine(); // consume newline

        // Fetch food price from the foods.txt file
        List<String> foods = FileHandler.readFile("data/foods.txt");
        double foodPrice = 0.0;
        for (String foodLine : foods) {
            String[] foodData = foodLine.split(",");
            if (Integer.parseInt(foodData[0]) == foodId) {
                foodPrice = Double.parseDouble(foodData[3]);
                break;
            }
        }

        // Calculate total amount
        double totalAmount = foodPrice * qty;

        // Creating a sample order
        String timestamp = new Date().toString();
        List<String> foodList = new ArrayList<>();
        foodList.add("Food" + foodId + "|" + qty);
        Order order = new Order(getNextOrderId(), username, restaurantId, foodList, timestamp, totalAmount);

        // Saving order to file
        List<String> orderData = new ArrayList<>();
        orderData.add(order.getOrderId() + "," + order.getCustomerUsername() + "," + order.getRestaurantId() + "," + order.getTimestamp() + "," + order.getFoodList().toString() + "," + order.getTotalAmount());
        FileHandler.writeFile("data/orders.txt", orderData, true);
        System.out.println("Order placed successfully! Waiting for confirmation.");
    }

    // View placed orders
    private void viewOrders(String username) {
        List<String> orders = FileHandler.readFile("data/orders.txt");
        for (String orderLine : orders) {
            String[] orderData = orderLine.split(",");
            if (orderData[1].equals(username)) {
                System.out.println("Order ID: " + orderData[0] + ", Restaurant ID: " + orderData[2] + ", Order Details: " + orderData[4] + ", Timestamp: " + orderData[3]);
            }
        }
    }

    // Get next available order ID
    private int getNextOrderId() {
        List<String> orders = FileHandler.readFile("data/orders.txt");
        return orders.size() + 1;
    }
}
