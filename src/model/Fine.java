package model;

import java.time.LocalDate;

public class Fine {
    private String memberId;
    private String bookIsbn;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double amount;
    private boolean paid;
    
    public Fine(String memberId, String bookIsbn, LocalDate issueDate, LocalDate dueDate) {
        this.memberId = memberId;
        this.bookIsbn = bookIsbn;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.amount = 0.0;
        this.paid = false;
    }
    
    // Calculate fine based on days overdue
    public void calculateFine(LocalDate returnDate) {
        this.returnDate = returnDate;
        
        if (returnDate.isAfter(dueDate)) {
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
            this.amount = daysOverdue * 2.0; // $2 per day fine
        } else {
            this.amount = 0.0;
        }
    }
    
    // Mark fine as paid
    public void payFine() {
        this.paid = true;
    }
    
    // Getters and Setters
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    
    public String getBookIsbn() { return bookIsbn; }
    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }
    
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
    
    @Override
    public String toString() {
        return memberId + "," + bookIsbn + "," + issueDate + "," + dueDate + "," + 
               (returnDate != null ? returnDate : "null") + "," + amount + "," + paid;
    }
    
    public static Fine fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length != 7) return null;
        
        try {
            Fine fine = new Fine(parts[0], parts[1], LocalDate.parse(parts[2]), LocalDate.parse(parts[3]));
            
            if (!"null".equals(parts[4])) {
                fine.setReturnDate(LocalDate.parse(parts[4]));
            }
            
            fine.setAmount(Double.parseDouble(parts[5]));
            fine.setPaid(Boolean.parseBoolean(parts[6]));
            
            return fine;
        } catch (Exception e) {
            return null;
        }
    }
    
    public String toDisplayString() {
        return String.format("Member: %s | Book: %s | Issued: %s | Due: %s | Returned: %s | Fine: $%.2f | Paid: %s",
                memberId, bookIsbn, issueDate, dueDate, 
                returnDate != null ? returnDate : "Not returned", 
                amount, paid ? "Yes" : "No");
    }
}