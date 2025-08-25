package model;

public class Customer extends User {
    private String email;
    private String phoneNumber;


    public Customer(String username, String password, String email, String phoneNumber) {
        super(username, password, "customer");
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
