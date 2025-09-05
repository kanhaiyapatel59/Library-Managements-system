package util;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputHelper {
    private static Scanner scanner = new Scanner(System.in);
    
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    public static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    public static LocalDate getDateInput(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                System.out.print(prompt + " (YYYY-MM-DD): ");
                String dateString = scanner.nextLine().trim();
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid date in YYYY-MM-DD format.");
            }
        }
    }
    
    public static void pressAnyKeyToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing screen fails, just print some empty lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}