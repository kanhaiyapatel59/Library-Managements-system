package service;

public class AuthService {
    
    public String authenticate(String username, String password) {
        // Simple hardcoded authentication - ALWAYS WORKS
        if ("admin".equals(username) && "admin123".equals(password)) {
            return "administrator";
        } else if ("librarian".equals(username) && "lib123".equals(password)) {
            return "librarian";
        } else if ("user".equals(username) && "user123".equals(password)) {
            return "member";
        }
        
        return null; // Authentication failed
    }
    
    public boolean registerUser(String username, String password, String role) {
        System.out.println("User registration would happen here");
        return true;
    }
    
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        System.out.println("Password change would happen here");
        return true;
    }
}