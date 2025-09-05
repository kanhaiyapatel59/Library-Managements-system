package model;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private int publicationYear;
    private int quantity;
    private int issuedCount;
    
    public Book(String isbn, String title, String author, String category, int publicationYear, int quantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publicationYear = publicationYear;
        this.quantity = quantity;
        this.issuedCount = 0;
    }
    
    // Getters and Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public int getIssuedCount() { return issuedCount; }
    public void setIssuedCount(int issuedCount) { this.issuedCount = issuedCount; }
    
    public boolean isAvailable() {
        return quantity > issuedCount;
    }
    
    public int getAvailableCopies() {
        return quantity - issuedCount;
    }
    
    @Override
    public String toString() {
        return isbn + "," + title + "," + author + "," + category + "," + 
               publicationYear + "," + quantity + "," + issuedCount;
    }
    
    public static Book fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length != 7) return null;
        
        try {
            Book book = new Book(
                parts[0], 
                parts[1], 
                parts[2], 
                parts[3], 
                Integer.parseInt(parts[4]), 
                Integer.parseInt(parts[5])
            );
            book.setIssuedCount(Integer.parseInt(parts[6]));
            return book;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public String toDisplayString() {
        return String.format("ISBN: %s | Title: %s | Author: %s | Category: %s | Year: %d | Available: %d/%d",
                isbn, title, author, category, publicationYear, getAvailableCopies(), quantity);
    }
}