package menu;

import service.LibraryService;
import service.AuthService;
import util.InputHelper;
import util.ConsoleColors;

public class LibraryMenu {
    private LibraryService libraryService;
    private AuthService authService;
    private String userRole;
    private String username;
    
    public LibraryMenu(String userRole, String username) {
        this.libraryService = new LibraryService();
        this.authService = new AuthService();
        this.userRole = userRole;
        this.username = username;
    }
    
    public void showMainMenu() {
        while (true) {
            InputHelper.clearScreen();
            System.out.println(ConsoleColors.BLUE_BOLD + "\n=== Smart Library Management System ===" + ConsoleColors.RESET);
            System.out.println("Logged in as: " + username + " (" + userRole + ")");
            System.out.println("\nMain Menu:");
            
            // Common options for all users
            System.out.println("1. Search Books");
            System.out.println("2. View All Books");
            
            // Additional options for members and above
            if (!"member".equals(userRole)) {
                System.out.println("3. Add Book");
                System.out.println("4. View All Members");
                System.out.println("5. Issue Book");
                System.out.println("6. Return Book");
                System.out.println("7. View Issued Books");
                System.out.println("8. View Overdue Books");
                System.out.println("9. Manage Fines");
            }
            
            // Admin-only options
            if ("administrator".equals(userRole)) {
                System.out.println("10. Add Member");
                System.out.println("11. Generate Reports");
                System.out.println("12. User Management");
            }
            
            System.out.println("0. Logout");
            
            int choice = InputHelper.getIntInput("\nPlease choose an option: ");
            
            switch (choice) {
                case 1:
                    libraryService.searchBooks();
                    break;
                case 2:
                    libraryService.viewAllBooks();
                    break;
                case 3:
                    if (!"member".equals(userRole)) {
                        libraryService.addBook();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! You don't have permission for this operation." + ConsoleColors.RESET);
                    }
                    break;
                case 4:
                    if (!"member".equals(userRole)) {
                        libraryService.viewAllMembers();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! You don't have permission for this operation." + ConsoleColors.RESET);
                    }
                    break;
                case 5:
                    if (!"member".equals(userRole)) {
                        libraryService.issueBook();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! You don't have permission for this operation." + ConsoleColors.RESET);
                    }
                    break;
                case 6:
                    if (!"member".equals(userRole)) {
                        libraryService.returnBook();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! You don't have permission for this operation." + ConsoleColors.RESET);
                    }
                    break;
                case 7:
                    if (!"member".equals(userRole)) {
                        libraryService.viewIssuedBooks();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! You don't have permission for this operation." + ConsoleColors.RESET);
                    }
                    break;
                case 8:
                    if (!"member".equals(userRole)) {
                        libraryService.viewOverdueBooks();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! You don't have permission for this operation." + ConsoleColors.RESET);
                    }
                    break;
                case 9:
                    if (!"member".equals(userRole)) {
                        libraryService.viewFines();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! You don't have permission for this operation." + ConsoleColors.RESET);
                    }
                    break;
                case 10:
                    if ("administrator".equals(userRole)) {
                        libraryService.addMember();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! Administrator permission required." + ConsoleColors.RESET);
                    }
                    break;
                case 11:
                    if ("administrator".equals(userRole)) {
                        libraryService.generateReports();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! Administrator permission required." + ConsoleColors.RESET);
                    }
                    break;
                case 12:
                    if ("administrator".equals(userRole)) {
                        showUserManagementMenu();
                    } else {
                        System.out.println(ConsoleColors.RED + "Access denied! Administrator permission required." + ConsoleColors.RESET);
                    }
                    break;
                case 0:
                    System.out.println(ConsoleColors.GREEN + "Logging out... Thank you for using the Library Management System!" + ConsoleColors.RESET);
                    return;
                default:
                    System.out.println(ConsoleColors.RED + "Invalid option! Please try again." + ConsoleColors.RESET);
            }
            
            InputHelper.pressAnyKeyToContinue();
        }
    }
    
    private void showUserManagementMenu() {
        while (true) {
            InputHelper.clearScreen();
            System.out.println(ConsoleColors.CYAN + "\n=== User Management ===" + ConsoleColors.RESET);
            System.out.println("1. Register New User");
            System.out.println("2. Change Password");
            System.out.println("3. Back to Main Menu");
            
            int choice = InputHelper.getIntInput("Choose option: ");
            
            switch (choice) {
                case 1:
                    registerNewUser();
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    return;
                default:
                    System.out.println(ConsoleColors.RED + "Invalid option!" + ConsoleColors.RESET);
            }
            
            InputHelper.pressAnyKeyToContinue();
        }
    }
    
    private void registerNewUser() {
        System.out.println(ConsoleColors.CYAN + "\n=== Register New User ===" + ConsoleColors.RESET);
        String newUsername = InputHelper.getStringInput("Enter username: ");
        String newPassword = InputHelper.getStringInput("Enter password: ");
        
        System.out.println("Select role:");
        System.out.println("1. Administrator");
        System.out.println("2. Librarian");
        System.out.println("3. Member");
        int roleChoice = InputHelper.getIntInput("Choose role: ");
        
        String role;
        switch (roleChoice) {
            case 1: role = "administrator"; break;
            case 2: role = "librarian"; break;
            default: role = "member"; break;
        }
        
        authService.registerUser(newUsername, newPassword, role);
    }
    
    private void changePassword() {
        System.out.println(ConsoleColors.CYAN + "\n=== Change Password ===" + ConsoleColors.RESET);
        String oldPassword = InputHelper.getStringInput("Enter current password: ");
        String newPassword = InputHelper.getStringInput("Enter new password: ");
        String confirmPassword = InputHelper.getStringInput("Confirm new password: ");
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println(ConsoleColors.RED + "New passwords don't match!" + ConsoleColors.RESET);
            return;
        }
        
        authService.changePassword(username, oldPassword, newPassword);
    }
}