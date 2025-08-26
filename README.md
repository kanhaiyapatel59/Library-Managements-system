1. Project Description
A console-based Java application to manage library operations like adding, searching, issuing, and returning books. It reduces manual work and maintains member and book records efficiently.
2. Features
User Management: Add, update, remove members.
Book Management: Add, search, update, delete books.
Issue/Return: Track issued books and calculate fines.
Reports: View available, issued, or overdue books.
Authentication: Simple Admin/User login.
3. Technology Used
Language: Java (Core Java)
Storage: File handling (.txt files); database upgrade later (MySQL/MongoDB)
IDE: IntelliJ/Eclipse/VS Code
4. Future Enhancements
GUI using JavaFX/Swing
mongoDB
Database integration
Role-based access
Fine calculation automation
Barcode/QR scanning for books

SmartLibraryManagementSystem/
│
├── src/
│   ├── Main.java                // Entry point of the program
│   ├── model/
│   │   ├── Book.java            // Book class (POJO)
│   │   ├── Member.java          // Member class (POJO)
│   │
│   ├── service/
│   │   ├── LibraryService.java  // Core logic: add/search/issue/return books
│   │
│   ├── util/
│   │   ├── FileHandler.java     // For saving/loading data from files
│   │   ├── InputHelper.java     // For console input validation
│   │
│   ├── menu/
│       ├── LibraryMenu.java     // Menu system for console operations
│
├── data/
│   ├── books.txt                // Stores book records
│   ├── members.txt              // Stores member records
│   ├── issued_books.txt         // Stores issue records
│
├── README.md                    // Project documentation
└── SmartLibraryManagementSystem.iml (if using IntelliJ)
6. Project Structure Diagram
Here’s a console-based architecture diagram:
            +-------------------------+
            |       Main.java         |
            +-----------+-------------+
                        |
                        v
            +-----------+-------------+
            |       LibraryMenu        |
            +-----------+-------------+
                        |
          +-------------+-------------+
          |                           |
          v                           v
+-------------------+        +-------------------+
|   LibraryService   |        |    FileHandler     |
+---------+---------+        +---------+---------+
          |                           |
          v                           v
+-------------------+        +-------------------+
|       Book         |        |     Member         |
+-------------------+        +-------------------+
