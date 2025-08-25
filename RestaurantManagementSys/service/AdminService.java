package service;

import model.Order;
import model.Restaurant;
import model.Food;
import util.FileHandler;
import java.util.*;

public class AdminService {


    public boolean adminLogin(Scanner sc) {
        System.out.println("Enter Admin Username:");
        String username = sc.nextLine();

        System.out.println("Enter Admin Password:");
        String password = sc.nextLine();

        // Hardcoded credentials for demonstration
        String storedUsername = "admin";
        String storedPassword = "password123";

        // Check if the entered credentials match
        if (username.equals(storedUsername) && password.equals(storedPassword)) {
            System.out.println("Login successful!");
            System.out.println("Access granted to the admin dashboard.");
            // Show the admin dashboard options
            showAdminDashboard(sc);
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    private void showAdminDashboard(Scanner sc) {
        while (true) {
            System.out.println("\nAdmin Dashboard:");
            System.out.println("1. Add Restaurant");
            System.out.println("2. Update Restaurant");
            System.out.println("3. Delete Restaurant");
            System.out.println("4. Add Food");
            System.out.println("5. Update Food");
            System.out.println("6. Delete Food");
            System.out.println("7. Toggle Food Availability");
            System.out.println("8. Process Pending Orders");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline character

            switch (choice) {
                case 1 -> addRestaurant(sc);
                case 2 -> updateRestaurant(sc);
                case 3 -> deleteRestaurant(sc);
                case 4 -> addFood(sc);
                case 5 -> updateFood(sc);
                case 6 -> deleteFood(sc);
                case 7 -> toggleFoodAvailability(sc);
                case 8 -> processPendingOrders(sc);
                case 9 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // New method to process pending orders
    private void processPendingOrders(Scanner sc) {
        List<String> ordersFromFile = FileHandler.readFile("data/orders.txt");

        if (ordersFromFile.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        Queue<String> orderQueue = new LinkedList<>(ordersFromFile);
        List<String> updatedOrders = new ArrayList<>(ordersFromFile); // To track unprocessed orders

        System.out.println("\nPending Orders:");

        while (!orderQueue.isEmpty()) {
            String orderData = orderQueue.poll(); // FIFO
            String[] orderFields = orderData.split(",", 6); // ensure only 6 splits

            if (orderFields.length < 6) {
                System.out.println("Skipping malformed order: " + orderData);
                continue;
            }

            try {
                int orderId = Integer.parseInt(orderFields[0]);
                String customerUsername = orderFields[1];
                int restaurantId = Integer.parseInt(orderFields[2]);
                String timestamp = orderFields[3];
                String foodListData = orderFields[4];
                double totalAmount = Double.parseDouble(orderFields[5]);

                System.out.println("\nProcessing order ID: " + orderId);
                System.out.println("Customer: " + customerUsername);
                System.out.println("Restaurant ID: " + restaurantId);
                System.out.println("Timestamp: " + timestamp);
                System.out.println("Food Items:");

                // Clean brackets and split on ; for multiple food items
                String cleanFoodList = foodListData.replaceAll("[\\[\\]]", "");
                String[] foodItems = cleanFoodList.split(";");

                for (String item : foodItems) {
                    String[] parts = item.split("\\|");
                    if (parts.length == 2) {
                        System.out.println(" - " + parts[0] + " x" + parts[1]);
                    } else {
                        System.out.println(" - Invalid food item format: " + item);
                    }
                }

                System.out.println("Total: ₹" + totalAmount);

                // Ask admin for confirmation
                System.out.print("Do you want to process this order? (yes/no): ");
                String input = sc.nextLine().trim();

                if (input.equalsIgnoreCase("yes")) {
                    updatedOrders.remove(orderData);
                    System.out.println("Order ID " + orderId + " has been processed and removed from pending list.");
                }

            } catch (Exception e) {
                System.out.println("Error processing order: " + orderData);
                e.printStackTrace();
            }
        }

        // Write back updated orders (removing processed ones)
        FileHandler.writeFile("data/orders.txt", updatedOrders, false);
    }
    // Add new restaurant
    public void addRestaurant(Scanner sc) {
        System.out.println("Enter Restaurant Name:");
        String name = sc.nextLine();
        System.out.println("Enter Restaurant Location:");
        String location = sc.nextLine();

        int id = getNextRestaurantId(); // Get next available restaurant ID
        Restaurant restaurant = new Restaurant(id, name, location);

        List<String> restaurantData = new ArrayList<>();
        restaurantData.add(restaurant.getId() + "," + restaurant.getName() + "," + restaurant.getLocation());

        FileHandler.writeFile("data/restaurants.txt", restaurantData, true);
        System.out.println("Restaurant added successfully!");
    }

    // Update restaurant details
    public void updateRestaurant(Scanner sc) {
        System.out.println("Enter Restaurant ID to Update:");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline

        List<String> restaurants = FileHandler.readFile("data/restaurants.txt");
        for (int i = 0; i < restaurants.size(); i++) {
            String[] data = restaurants.get(i).split(",");
            if (Integer.parseInt(data[0]) == id) {
                System.out.println("Enter New Restaurant Name:");
                data[1] = sc.nextLine();
                System.out.println("Enter New Location:");
                data[2] = sc.nextLine();
                restaurants.set(i, String.join(",", data));
                FileHandler.overwriteFile("data/restaurants.txt", restaurants);
                System.out.println("Restaurant updated successfully!");
                return;
            }
        }
        System.out.println("Restaurant not found!");
    }

    // Delete restaurant
    public void deleteRestaurant(Scanner sc) {
        System.out.println("Enter Restaurant ID to Delete:");
        int id = sc.nextInt();
        sc.nextLine();

        List<String> restaurants = FileHandler.readFile("data/restaurants.txt");
        boolean found = false;
        for (int i = 0; i < restaurants.size(); i++) {
            String[] data = restaurants.get(i).split(",");
            if (Integer.parseInt(data[0]) == id) {
                restaurants.remove(i);
                found = true;
                break;
            }
        }

        if (found) {
            FileHandler.overwriteFile("data/restaurants.txt", restaurants);
            System.out.println("Restaurant deleted successfully!");
        } else {
            System.out.println("Restaurant not found!");
        }
    }

    // Add new food to a restaurant
    public void addFood(Scanner sc) {
        System.out.println("Enter Restaurant ID to Add Food:");
        int restaurantId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.println("Enter Food Name:");
        String name = sc.nextLine();

        System.out.println("Enter Food Price:");
        double price = sc.nextDouble();
        sc.nextLine(); // consume newline

        System.out.println("Is the food available? (true/false):");
        boolean isAvailable = sc.nextBoolean();

        int foodId = getNextFoodId(); // Get next available food ID
        Food food = new Food(foodId, restaurantId, name, price, isAvailable);

        List<String> foodData = new ArrayList<>();
        foodData.add(food.getFoodId() + "," + food.getRestaurantId() + "," + food.getName() + "," + food.getPrice() + "," + food.isAvailable());

        FileHandler.writeFile("data/foods.txt", foodData, true);
        System.out.println("Food added successfully!");
    }

    // Mark food as available/unavailable
    public void toggleFoodAvailability(Scanner sc) {
        System.out.println("Enter Food ID to Toggle Availability:");
        int foodId = sc.nextInt();

        List<String> foods = FileHandler.readFile("data/foods.txt");
        for (int i = 0; i < foods.size(); i++) {
            String[] data = foods.get(i).split(",");
            if (Integer.parseInt(data[0]) == foodId) {
                boolean isAvailable = Boolean.parseBoolean(data[4]);
                data[4] = String.valueOf(!isAvailable);  // Toggle availability
                foods.set(i, String.join(",", data));
                FileHandler.overwriteFile("data/foods.txt", foods);
                System.out.println("Food availability updated successfully!");
                return;
            }
        }
        System.out.println("Food not found!");
    }

    // Get next available restaurant ID
    private int getNextRestaurantId() {
        List<String> restaurants = FileHandler.readFile("data/restaurants.txt");
        return restaurants.size() + 1;
    }

    // Get next available food ID
    private int getNextFoodId() {
        List<String> foods = FileHandler.readFile("data/foods.txt");
        return foods.size() + 1;
    }
    public void updateFood(Scanner sc) {
        System.out.print("Enter Food ID to Update: ");
        String input = sc.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Food ID cannot be empty!");
            return;
        }

        int foodId;
        try {
            foodId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Food ID. Please enter a valid number.");
            return;
        }

        List<String> foods = FileHandler.readFile("data/foods.txt");
        for (int i = 0; i < foods.size(); i++) {
            String[] data = foods.get(i).split(",");
            if (Integer.parseInt(data[0]) == foodId) {
                System.out.print("Enter New Food Name: ");
                data[2] = sc.nextLine();

                System.out.print("Enter New Food Price: ");
                String priceInput = sc.nextLine().trim();
                try {
                    Double.parseDouble(priceInput); // validate price
                    data[3] = priceInput;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price entered. Update cancelled.");
                    return;
                }

                System.out.print("Is the food available? (true/false): ");
                String availabilityInput = sc.nextLine().trim().toLowerCase();
                if (!availabilityInput.equals("true") && !availabilityInput.equals("false")) {
                    System.out.println("Invalid availability input. Must be 'true' or 'false'.");
                    return;
                }
                data[4] = availabilityInput;

                foods.set(i, String.join(",", data));
                FileHandler.overwriteFile("data/foods.txt", foods);
                System.out.println("Food updated successfully!");
                return;
            }
        }

        System.out.println("Food ID not found.");
    }
    public void deleteFood(Scanner sc) {
        System.out.println("Enter Food ID to Delete:");
        int foodId = sc.nextInt();
        sc.nextLine(); // consume newline

        List<String> foods = FileHandler.readFile("data/foods.txt");
        boolean found = false;
        for (int i = 0; i < foods.size(); i++) {
            String[] data = foods.get(i).split(",");
            if (Integer.parseInt(data[0]) == foodId) {
                foods.remove(i);
                found = true;
                break;
            }
        }

        if (found) {
            FileHandler.overwriteFile("data/foods.txt", foods);
            System.out.println("Food deleted successfully!");
        } else {
            System.out.println("Food not found!");
        }}
}
