package com.university.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static IDataService dataService = new DatabaseService();
    private static Scanner scanner = new Scanner(System.in);
    
    // CONCEPT: Encapsulation - Using a Map to store menu options
    private static Map<Integer, String> menuOptions = new HashMap<>();

    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            IDataService service = new DatabaseService();
            MainWindow window = new MainWindow(service);
            window.setVisible(true);
        });
    }

    // Console methods kept for reference or alternative usage
    /*
    private static void initializeMenu() {
        menuOptions.put(1, "Add a new Student");
        // ... (rest of the console logic is commented out or preserved if needed)
    }
    */


    private static void initializeMenu() {
        menuOptions.put(1, "Add a new Student");
        menuOptions.put(2, "Assign/Update Grade");
        menuOptions.put(3, "View all Students");
        menuOptions.put(4, "Update Student Details");
        menuOptions.put(5, "Delete a Student");
        menuOptions.put(6, "Exit");
    }

    private static void printMenu() {
        System.out.println("\n--- Student Management System ---");
        // Iterating over a Map
        for (Map.Entry<Integer, String> entry : menuOptions.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }

    private static void addStudent() {
        System.out.println("\nAdding New Student");
        String id = getStringInput("Enter Student ID: ");
        
        // CONCEPT: Validate non-empty ID
        if (id.trim().isEmpty()) {
            System.out.println("Error: ID cannot be empty.");
            return;
        }

        if (dataService.getStudent(id) != null) {
            System.out.println("Error: Student with this ID already exists.");
            return;
        }

        String fName = getStringInput("Enter First Name: ");
        String lName = getStringInput("Enter Last Name: ");
        int age = getIntInput("Enter Age: ");
        double grade = getDoubleInput("Enter Initial Grade (0-100): ");

        // CONCEPT: Validate grade range
        if (grade < 0 || grade > 100) {
            System.out.println("Error: Grade must be between 0 and 100.");
            return;
        }

        Student s = new Student(fName, lName, age, id, grade);
        dataService.addStudent(s);
    }

    private static void assignGrade() {
        String id = getStringInput("Enter Student ID to grade: ");
        Student s = dataService.getStudent(id);
        
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        
        double newGrade = getDoubleInput("Enter New Grade (0-100): ");
         if (newGrade < 0 || newGrade > 100) {
            System.out.println("Error: Grade must be between 0 and 100.");
            return;
        }
        
        s.setGrade(newGrade);
        try {
            dataService.updateStudent(s);
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewAllStudents() {
        List<Student> list = dataService.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : list) {
                System.out.println("----------------");
                s.displayInfo(); // Polymorphic call
            }
            System.out.println("----------------");
        }
    }

    private static void updateStudent() {
        String id = getStringInput("Enter Student ID to update: ");
        Student s = dataService.getStudent(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Leave blank to keep current value.");
        
        String newFName = getStringInput("New First Name (" + s.getFirstName() + "): ");
        if (!newFName.isEmpty()) s.setFirstName(newFName);

        String newLName = getStringInput("New Last Name (" + s.getLastName() + "): ");
        if (!newLName.isEmpty()) s.setLastName(newLName);

        String ageStr = getStringInput("New Age (" + s.getAge() + "): ");
        if (!ageStr.isEmpty()) {
            try {
                int newAge = Integer.parseInt(ageStr);
                s.setAge(newAge);
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format. Keeping old age.");
            }
        }
        
        try {
            dataService.updateStudent(s);
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteStudent() {
        String id = getStringInput("Enter Student ID to delete: ");
        try {
            dataService.deleteStudent(id);
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Helper methods for input
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
