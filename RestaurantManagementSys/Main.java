import service.AdminService;
import service.CustomerService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdminService adminService = new AdminService();
        CustomerService customerService = new CustomerService();

        while (true) {
            System.out.println("\nWelcome to Restaurant Management System");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Customer Register");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();  // consume the newline character

            switch (choice) {
                case 1:
                    // Admin login
                    adminService.adminLogin(sc);
                    break;
                case 2:
                    // Customer login
                    customerService.loginCustomer(sc);
                    break;
                case 3:
                    // Customer register
                    customerService.registerCustomer(sc);
                    break;
                case 4:
                    // Exit the application
                    System.out.println("Exiting the system.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
