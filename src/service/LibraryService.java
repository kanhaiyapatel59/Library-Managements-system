package service;

import model.Book;
import model.Member;
import model.Fine;
import util.FileHandler;
import util.InputHelper;
import util.ConsoleColors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private List<Book> books;
    private List<Member> members;
    private List<Fine> fines;
    private static final String BOOKS_FILE = "data/books.txt";
    private static final String MEMBERS_FILE = "data/members.txt";
    private static final String TRANSACTIONS_FILE = "data/transactions.txt";
    private static final String FINES_FILE = "data/fines.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public LibraryService() {
        loadData();
    }
    
    private void loadData() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        fines = new ArrayList<>();
        
        // Load books
        List<String> bookLines = FileHandler.readFile(BOOKS_FILE);
        for (String line : bookLines) {
            Book book = Book.fromString(line);
            if (book != null) {
                books.add(book);
            }
        }
        
        // Load members
        List<String> memberLines = FileHandler.readFile(MEMBERS_FILE);
        for (String line : memberLines) {
            Member member = Member.fromString(line);
            if (member != null) {
                members.add(member);
            }
        }
        
        // Load fines
        List<String> fineLines = FileHandler.readFile(FINES_FILE);
        for (String line : fineLines) {
            Fine fine = Fine.fromString(line);
            if (fine != null) {
                fines.add(fine);
            }
        }
    }
    
    private void saveBooks() {
        List<String> lines = new ArrayList<>();
        for (Book book : books) {
            lines.add(book.toString());
        }
        FileHandler.writeFile(BOOKS_FILE, lines);
    }
    
    private void saveMembers() {
        List<String> lines = new ArrayList<>();
        for (Member member : members) {
            lines.add(member.toString());
        }
        FileHandler.writeFile(MEMBERS_FILE, lines);
    }
    
    private void saveFines() {
        List<String> lines = new ArrayList<>();
        for (Fine fine : fines) {
            lines.add(fine.toString());
        }
        FileHandler.writeFile(FINES_FILE, lines);
    }
    
    public void addBook() {
        System.out.println(ConsoleColors.CYAN + "\n=== Add New Book ===" + ConsoleColors.RESET);
        
        String isbn = InputHelper.getStringInput("Enter ISBN: ");
        // Check if book already exists
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                System.out.println(ConsoleColors.RED + "A book with this ISBN already exists!" + ConsoleColors.RESET);
                return;
            }
        }
        
        String title = InputHelper.getStringInput("Enter title: ");
        String author = InputHelper.getStringInput("Enter author: ");
        String category = InputHelper.getStringInput("Enter category: ");
        int publicationYear = InputHelper.getIntInput("Enter publication year: ");
        int quantity = InputHelper.getIntInput("Enter quantity: ");
        
        Book newBook = new Book(isbn, title, author, category, publicationYear, quantity);
        books.add(newBook);
        saveBooks();
        System.out.println(ConsoleColors.GREEN + "Book added successfully!" + ConsoleColors.RESET);
    }
    
    public void addMember() {
        System.out.println(ConsoleColors.CYAN + "\n=== Add New Member ===" + ConsoleColors.RESET);
        
        String memberId = InputHelper.getStringInput("Enter member ID: ");
        // Check if member already exists
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                System.out.println(ConsoleColors.RED + "A member with this ID already exists!" + ConsoleColors.RESET);
                return;
            }
        }
        
        String name = InputHelper.getStringInput("Enter name: ");
        String email = InputHelper.getStringInput("Enter email: ");
        String phone = InputHelper.getStringInput("Enter phone: ");
        
        System.out.println("Membership types:");
        System.out.println("1. Regular (max 5 books)");
        System.out.println("2. Premium (max 10 books)");
        int typeChoice = InputHelper.getIntInput("Choose membership type: ");
        
        String membershipType = (typeChoice == 2) ? "premium" : "regular";
        
        Member newMember = new Member(memberId, name, email, phone, membershipType);
        members.add(newMember);
        saveMembers();
        System.out.println(ConsoleColors.GREEN + "Member added successfully!" + ConsoleColors.RESET);
    }
    
    public void searchBooks() {
        System.out.println(ConsoleColors.CYAN + "\n=== Search Books ===" + ConsoleColors.RESET);
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Category");
        System.out.println("4. Search by ISBN");
        int choice = InputHelper.getIntInput("Choose search option: ");
        
        String searchTerm = InputHelper.getStringInput("Enter search term: ").toLowerCase();
        List<Book> results = new ArrayList<>();
        
        for (Book book : books) {
            boolean match = false;
            switch (choice) {
                case 1: match = book.getTitle().toLowerCase().contains(searchTerm); break;
                case 2: match = book.getAuthor().toLowerCase().contains(searchTerm); break;
                case 3: match = book.getCategory().toLowerCase().contains(searchTerm); break;
                case 4: match = book.getIsbn().toLowerCase().contains(searchTerm); break;
                default: 
                    System.out.println(ConsoleColors.RED + "Invalid option!" + ConsoleColors.RESET);
                    return;
            }
            
            if (match) {
                results.add(book);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No books found matching your search." + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.GREEN + "\nSearch Results (" + results.size() + " found):" + ConsoleColors.RESET);
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i+1) + ". " + results.get(i).toDisplayString());
            }
        }
    }
    
    public void viewAllBooks() {
        System.out.println(ConsoleColors.CYAN + "\n=== All Books ===" + ConsoleColors.RESET);
        if (books.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No books in the library." + ConsoleColors.RESET);
            return;
        }
        
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i+1) + ". " + books.get(i).toDisplayString());
        }
    }
    
    public void viewAllMembers() {
        System.out.println(ConsoleColors.CYAN + "\n=== All Members ===" + ConsoleColors.RESET);
        if (members.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No members registered." + ConsoleColors.RESET);
            return;
        }
        
        for (int i = 0; i < members.size(); i++) {
            System.out.println((i+1) + ". " + members.get(i).toDisplayString());
        }
    }
    
    public void issueBook() {
        System.out.println(ConsoleColors.CYAN + "\n=== Issue Book ===" + ConsoleColors.RESET);
        String memberId = InputHelper.getStringInput("Enter member ID: ");
        String isbn = InputHelper.getStringInput("Enter book ISBN: ");
        
        Member member = findMemberById(memberId);
        Book book = findBookByIsbn(isbn);
        
        if (member == null) {
            System.out.println(ConsoleColors.RED + "Member not found!" + ConsoleColors.RESET);
            return;
        }
        
        if (book == null) {
            System.out.println(ConsoleColors.RED + "Book not found!" + ConsoleColors.RESET);
            return;
        }
        
        if (!book.isAvailable()) {
            System.out.println(ConsoleColors.RED + "Book is not available for issue!" + ConsoleColors.RESET);
            return;
        }
        
        // Check if member has reached max books limit
        int issuedCount = getIssuedBooksCount(memberId);
        if (issuedCount >= member.getMaxBooksAllowed()) {
            System.out.println(ConsoleColors.RED + "Member has reached the maximum allowed books (" + member.getMaxBooksAllowed() + ")!" + ConsoleColors.RESET);
            return;
        }
        
        // Update book issue count
        book.setIssuedCount(book.getIssuedCount() + 1);
        saveBooks();
        
        // Record the issue transaction
        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(14); // 2 weeks due date
        String transaction = memberId + "," + isbn + "," + issueDate + "," + dueDate + ",issued";
        
        FileHandler.appendToFile(TRANSACTIONS_FILE, transaction);
        System.out.println(ConsoleColors.GREEN + "Book issued successfully! Due date: " + dueDate + ConsoleColors.RESET);
    }
    
    public void returnBook() {
        System.out.println(ConsoleColors.CYAN + "\n=== Return Book ===" + ConsoleColors.RESET);
        String memberId = InputHelper.getStringInput("Enter member ID: ");
        String isbn = InputHelper.getStringInput("Enter book ISBN: ");
        
        // Find the issued book record
        List<String> transactionRecords = FileHandler.readFile(TRANSACTIONS_FILE);
        boolean found = false;
        Fine fine = null;
        
        for (int i = 0; i < transactionRecords.size(); i++) {
            String[] parts = transactionRecords.get(i).split(",");
            if (parts.length >= 5 && parts[0].equals(memberId) && parts[1].equals(isbn) && parts[4].equals("issued")) {
                // Mark as returned
                LocalDate returnDate = LocalDate.now();
                LocalDate dueDate = LocalDate.parse(parts[3]);
                transactionRecords.set(i, memberId + "," + isbn + "," + parts[2] + "," + parts[3] + ",returned," + returnDate);
                found = true;
                
                // Update book issue count
                Book book = findBookByIsbn(isbn);
                if (book != null) {
                    book.setIssuedCount(book.getIssuedCount() - 1);
                }
                
                // Check for fine if returned after due date
                if (returnDate.isAfter(dueDate)) {
                    fine = new Fine(memberId, isbn, LocalDate.parse(parts[2]), dueDate);
                    fine.calculateFine(returnDate);
                    fines.add(fine);
                    saveFines();
                    System.out.println(ConsoleColors.YELLOW + "Book returned late! Fine amount: $" + fine.getAmount() + ConsoleColors.RESET);
                }
                
                break;
            }
        }
        
        if (found) {
            FileHandler.writeFile(TRANSACTIONS_FILE, transactionRecords);
            saveBooks();
            System.out.println(ConsoleColors.GREEN + "Book returned successfully!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "No active issue record found for this member and book!" + ConsoleColors.RESET);
        }
    }
    
    public void viewFines() {
        System.out.println(ConsoleColors.CYAN + "\n=== Fines Management ===" + ConsoleColors.RESET);
        
        if (fines.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No fines recorded." + ConsoleColors.RESET);
            return;
        }
        
        // Show unpaid fines
        System.out.println("Unpaid Fines:");
        boolean hasUnpaid = false;
        for (Fine fine : fines) {
            if (!fine.isPaid()) {
                System.out.println(fine.toDisplayString());
                hasUnpaid = true;
            }
        }
        
        if (!hasUnpaid) {
            System.out.println(ConsoleColors.GREEN + "No unpaid fines." + ConsoleColors.RESET);
        }
        
        // Option to pay fines
        System.out.println("\n1. Pay a fine");
        System.out.println("2. Back to main menu");
        int choice = InputHelper.getIntInput("Choose option: ");
        
        if (choice == 1) {
            payFine();
        }
    }
    
    private void payFine() {
        String memberId = InputHelper.getStringInput("Enter member ID: ");
        String isbn = InputHelper.getStringInput("Enter book ISBN: ");
        
        for (Fine fine : fines) {
            if (fine.getMemberId().equals(memberId) && 
                fine.getBookIsbn().equals(isbn) && 
                !fine.isPaid()) {
                
                System.out.println("Fine amount: $" + fine.getAmount());
                String confirm = InputHelper.getStringInput("Pay this fine? (yes/no): ");
                
                if ("yes".equalsIgnoreCase(confirm)) {
                    fine.payFine();
                    saveFines();
                    System.out.println(ConsoleColors.GREEN + "Fine paid successfully!" + ConsoleColors.RESET);
                } else {
                    System.out.println("Fine payment cancelled.");
                }
                return;
            }
        }
        
        System.out.println(ConsoleColors.RED + "No unpaid fine found for this member and book." + ConsoleColors.RESET);
    }
    
    public void viewIssuedBooks() {
        System.out.println(ConsoleColors.CYAN + "\n=== Currently Issued Books ===" + ConsoleColors.RESET);
        
        List<String> transactionRecords = FileHandler.readFile(TRANSACTIONS_FILE);
        int count = 0;
        
        for (String record : transactionRecords) {
            String[] parts = record.split(",");
            if (parts.length >= 5 && parts[4].equals("issued")) {
                Book book = findBookByIsbn(parts[1]);
                Member member = findMemberById(parts[0]);
                
                if (book != null && member != null) {
                    System.out.println("Member: " + member.getName() + " (" + member.getMemberId() + ")");
                    System.out.println("Book: " + book.getTitle() + " (" + book.getIsbn() + ")");
                    System.out.println("Issued: " + parts[2] + " | Due: " + parts[3]);
                    System.out.println("----------------------------------------");
                    count++;
                }
            }
        }
        
        if (count == 0) {
            System.out.println(ConsoleColors.YELLOW + "No books currently issued." + ConsoleColors.RESET);
        } else {
            System.out.println("Total issued books: " + count);
        }
    }
    
    public void viewOverdueBooks() {
        System.out.println(ConsoleColors.CYAN + "\n=== Overdue Books ===" + ConsoleColors.RESET);
        
        List<String> transactionRecords = FileHandler.readFile(TRANSACTIONS_FILE);
        LocalDate today = LocalDate.now();
        int count = 0;
        
        for (String record : transactionRecords) {
            String[] parts = record.split(",");
            if (parts.length >= 5 && parts[4].equals("issued")) {
                LocalDate dueDate = LocalDate.parse(parts[3]);
                if (today.isAfter(dueDate)) {
                    Book book = findBookByIsbn(parts[1]);
                    Member member = findMemberById(parts[0]);
                    
                    if (book != null && member != null) {
                        long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dueDate, today);
                        System.out.println("Member: " + member.getName() + " (" + member.getMemberId() + ")");
                        System.out.println("Book: " + book.getTitle() + " (" + book.getIsbn() + ")");
                        System.out.println("Due: " + dueDate + " | Days overdue: " + daysOverdue);
                        System.out.println("----------------------------------------");
                        count++;
                    }
                }
            }
        }
        
        if (count == 0) {
            System.out.println(ConsoleColors.GREEN + "No overdue books." + ConsoleColors.RESET);
        } else {
            System.out.println("Total overdue books: " + count);
        }
    }
    
    public void generateReports() {
        System.out.println(ConsoleColors.CYAN + "\n=== Library Reports ===" + ConsoleColors.RESET);
        System.out.println("1. Available Books Report");
        System.out.println("2. Issued Books Report");
        System.out.println("3. Overdue Books Report");
        System.out.println("4. Member Statistics");
        int choice = InputHelper.getIntInput("Choose report type: ");
        
        switch (choice) {
            case 1:
                generateAvailableBooksReport();
                break;
            case 2:
                generateIssuedBooksReport();
                break;
            case 3:
                generateOverdueBooksReport();
                break;
            case 4:
                generateMemberStatistics();
                break;
            default:
                System.out.println(ConsoleColors.RED + "Invalid choice!" + ConsoleColors.RESET);
        }
    }
    
    private void generateAvailableBooksReport() {
        System.out.println(ConsoleColors.CYAN + "\n=== Available Books Report ===" + ConsoleColors.RESET);
        int availableCount = 0;
        
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book.toDisplayString());
                availableCount++;
            }
        }
        
        System.out.println("Total available books: " + availableCount + "/" + books.size());
    }
    
    private void generateIssuedBooksReport() {
        System.out.println(ConsoleColors.CYAN + "\n=== Issued Books Report ===" + ConsoleColors.RESET);
        List<String> transactionRecords = FileHandler.readFile(TRANSACTIONS_FILE);
        int issuedCount = 0;
        
        for (String record : transactionRecords) {
            String[] parts = record.split(",");
            if (parts.length >= 5 && parts[4].equals("issued")) {
                Book book = findBookByIsbn(parts[1]);
                Member member = findMemberById(parts[0]);
                
                if (book != null && member != null) {
                    System.out.println("Book: " + book.getTitle() + " | Member: " + member.getName() + 
                                     " | Issued: " + parts[2] + " | Due: " + parts[3]);
                    issuedCount++;
                }
            }
        }
        
        System.out.println("Total issued books: " + issuedCount);
    }
    
    private void generateOverdueBooksReport() {
        System.out.println(ConsoleColors.CYAN + "\n=== Overdue Books Report ===" + ConsoleColors.RESET);
        List<String> transactionRecords = FileHandler.readFile(TRANSACTIONS_FILE);
        LocalDate today = LocalDate.now();
        int overdueCount = 0;
        
        for (String record : transactionRecords) {
            String[] parts = record.split(",");
            if (parts.length >= 5 && parts[4].equals("issued")) {
                LocalDate dueDate = LocalDate.parse(parts[3]);
                if (today.isAfter(dueDate)) {
                    Book book = findBookByIsbn(parts[1]);
                    Member member = findMemberById(parts[0]);
                    
                    if (book != null && member != null) {
                        long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dueDate, today);
                        System.out.println("Book: " + book.getTitle() + " | Member: " + member.getName() + 
                                         " | Due: " + dueDate + " | Days overdue: " + daysOverdue);
                        overdueCount++;
                    }
                }
            }
        }
        
        System.out.println("Total overdue books: " + overdueCount);
    }
    
    private void generateMemberStatistics() {
        System.out.println(ConsoleColors.CYAN + "\n=== Member Statistics ===" + ConsoleColors.RESET);
        
        for (Member member : members) {
            int issuedCount = getIssuedBooksCount(member.getMemberId());
            System.out.println("Member: " + member.getName() + " | Type: " + member.getMembershipType() + 
                             " | Books issued: " + issuedCount + "/" + member.getMaxBooksAllowed());
        }
    }
    
    private int getIssuedBooksCount(String memberId) {
        List<String> transactionRecords = FileHandler.readFile(TRANSACTIONS_FILE);
        int count = 0;
        
        for (String record : transactionRecords) {
            String[] parts = record.split(",");
            if (parts.length >= 5 && parts[0].equals(memberId) && parts[4].equals("issued")) {
                count++;
            }
        }
        
        return count;
    }
    
    private Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
    
    private Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}