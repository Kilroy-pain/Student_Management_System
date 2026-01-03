package com.university.management;

// CONCEPT: Error Handling - Custom Exception Class
public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
