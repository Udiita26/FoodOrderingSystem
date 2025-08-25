package model;

public class Food {
    private int foodId;
    private int restaurantId;
    private String name;
    private double price;
    private boolean isAvailable;

    public Food(int foodId, int restaurantId, String name, double price, boolean isAvailable) {
        this.foodId = foodId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public int getFoodId() { return foodId; }
    public int getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}
