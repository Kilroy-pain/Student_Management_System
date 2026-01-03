package com.university.management;

// CONCEPT: Inheritance - Student inherits from Person
public class Student extends Person {
    private String studentId;
    private double grade;

    public Student(String firstName, String lastName, int age, String studentId, double grade) {
        super(firstName, lastName, age);
        this.studentId = studentId;
        this.grade = grade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    // CONCEPT: Polymorphism - Overriding displayInfo
    @Override
    public void displayInfo() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Age: " + getAge());
        System.out.println("Grade: " + grade);
    }
}
