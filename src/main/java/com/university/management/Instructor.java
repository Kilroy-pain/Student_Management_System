package com.university.management;

// CONCEPT: Inheritance - Instructor inherits from Person
public class Instructor extends Person {
    private String employeeId;
    private String department;

    // CONCEPT: Data types & variables - String usage
    public Instructor(String firstName, String lastName, int age, String employeeId, String department) {
        super(firstName, lastName, age);
        this.employeeId = employeeId;
        this.department = department;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDepartment() {
        return department;
    }

    // CONCEPT: Polymorphism - Overriding displayInfo differently from Student
    @Override
    public void displayInfo() {
        System.out.println("Instructor ID: " + employeeId);
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Department: " + department);
    }
}
