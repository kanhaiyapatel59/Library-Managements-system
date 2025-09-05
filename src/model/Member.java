package model;

import java.time.LocalDate;

public class Member {
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private String membershipType;
    private LocalDate registrationDate;
    private int maxBooksAllowed;
    
    public Member(String memberId, String name, String email, String phone, String membershipType) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipType = membershipType;
        this.registrationDate = LocalDate.now();
        
        // Set max books based on membership type
        if ("premium".equalsIgnoreCase(membershipType)) {
            this.maxBooksAllowed = 10;
        } else {
            this.maxBooksAllowed = 5;
        }
    }
    
    // Getters and Setters
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { 
        this.membershipType = membershipType;
        // Update max books when membership type changes
        if ("premium".equalsIgnoreCase(membershipType)) {
            this.maxBooksAllowed = 10;
        } else {
            this.maxBooksAllowed = 5;
        }
    }
    
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    
    public int getMaxBooksAllowed() { return maxBooksAllowed; }
    
    @Override
    public String toString() {
        return memberId + "," + name + "," + email + "," + phone + "," + 
               membershipType + "," + registrationDate + "," + maxBooksAllowed;
    }
    
    public static Member fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length != 7) return null;
        
        try {
            Member member = new Member(parts[0], parts[1], parts[2], parts[3], parts[4]);
            member.setRegistrationDate(LocalDate.parse(parts[5]));
            // maxBooksAllowed is set automatically based on membership type
            return member;
        } catch (Exception e) {
            return null;
        }
    }
    
    public String toDisplayString() {
        return String.format("ID: %s | Name: %s | Email: %s | Phone: %s | Type: %s | Registered: %s | Max Books: %d",
                memberId, name, email, phone, membershipType, registrationDate, maxBooksAllowed);
    }
}