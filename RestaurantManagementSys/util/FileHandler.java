package util;

import model.Order;

import java.io.*;
import java.util.*;

public class FileHandler {
    public static void saveOrder(Order order) {
        try {
            FileWriter writer = new FileWriter("data/orders.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // Write order details to file
            bufferedWriter.write("Order ID: " + order.getOrderId() + "\n");
            bufferedWriter.write("Customer Name: " + order.getCustomerUsername() + "\n");
            bufferedWriter.write("Items: " + order.getFoodList().toString() + "\n");
            bufferedWriter.write("Total Amount: ₹" + order.totalAmount + "\n");
            bufferedWriter.write("------------\n");

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to orders file: " + e.getMessage());
        }
    }

    // Method to load orders from the file (optional)
    public static List<Order> loadOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            FileReader reader = new FileReader("data/orders.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {

            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading from orders file: " + e.getMessage());
        }
        return orders;
    }

    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null)
                lines.add(line);
        } catch (IOException e) {
            System.out.println("Error reading " + path);
        }
        return lines;
    }

    public static void writeFile(String path, List<String> lines, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, append))) {
            for (String line : lines)
                bw.write(line + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to " + path);
        }
    }

    public static void overwriteFile(String path, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String line : lines)
                bw.write(line + "\n");
        } catch (IOException e) {
            System.out.println("Error overwriting " + path);
        }
    }
}

