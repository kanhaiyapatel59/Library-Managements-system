import menu.LibraryMenu;
import service.AuthService;
import util.InputHelper;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Welcome to Smart Library Management System ===");
        
        // Initialize authentication service
        AuthService authService = new AuthService();
        
        boolean loggedIn = false;
        String userRole = "";
        String username = "";
        
        while (!loggedIn) {
            System.out.println("\nPlease login to continue");
            username = InputHelper.getStringInput("Username: ");
            String password = InputHelper.getStringInput("Password: ");
            
            userRole = authService.authenticate(username, password);
            if (userRole != null) {
                loggedIn = true;
                System.out.println("Login successful! Welcome " + username + " (" + userRole + ")");
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
        
        // Start the appropriate menu based on user role
        LibraryMenu menu = new LibraryMenu(userRole, username);
        menu.showMainMenu();
    }
}