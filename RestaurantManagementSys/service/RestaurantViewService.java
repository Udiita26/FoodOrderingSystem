package service;

import model.Restaurant;
import model.Food;
import util.FileHandler;

import java.util.*;

public class RestaurantViewService {
    OrderService orderService = new OrderService();

    public void showRestaurantOptions() {
        // Example option to show orders and process them
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. View Orders");
        System.out.println("2. Process Order");
        System.out.println("Enter your choice: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                orderService.viewOrders();
                break;
            case 2:
                orderService.processOrder();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    // View all restaurants
    public void viewRestaurants() {
        List<String> restaurants = FileHandler.readFile("data/restaurants.txt");
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }
        System.out.println("\nAvailable Restaurants:");
        for (String restaurantLine : restaurants) {
            String[] data = restaurantLine.split(",");
            System.out.println("Restaurant ID: " + data[0] + ", Name: " + data[1] + ", Location: " + data[2]);
        }
    }

    // View food items available at a restaurant
    public void viewRestaurantMenu(int restaurantId) {
        List<String> foods = FileHandler.readFile("data/foods.txt");
        boolean found = false;

        System.out.println("\nFood Menu for Restaurant ID: " + restaurantId);
        for (String foodLine : foods) {
            String[] data = foodLine.split(",");
            if (Integer.parseInt(data[1]) == restaurantId && Boolean.parseBoolean(data[4])) { // Check if food is available
                System.out.println("Food ID: " + data[0] + ", Name: " + data[2] + ", Price: " + data[3]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No food available for this restaurant.");
        }
    }
}
