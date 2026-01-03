package com.university.management;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// CONCEPT: Database Support - JDBC Implementation
public class DatabaseService implements IDataService {
    // CONCEPT: Encapsulation - Private connection string
    private static final String CONNECTION_STRING = "jdbc:sqlite:university.db";

    @Override
    public void initializeDatabase() {
        // Prepare the creation SQL. In real apps, this might be loaded from a file.
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "student_id TEXT NOT NULL UNIQUE, " +
                     "first_name TEXT NOT NULL, " +
                     "last_name TEXT NOT NULL, " +
                     "age INTEGER NOT NULL, " +
                     "grade DOUBLE DEFAULT 0.0" +
                     ");";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database initialized.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO students(student_id, first_name, last_name, age, grade) VALUES(?,?,?,?,?)";

        // CONCEPT: Error Handling - Try-catch-resources
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setInt(4, student.getAge());
            pstmt.setDouble(5, student.getGrade());
            
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    @Override
    public void updateStudent(Student student) throws StudentNotFoundException {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, age = ?, grade = ? WHERE student_id = ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setInt(3, student.getAge());
            pstmt.setDouble(4, student.getGrade());
            pstmt.setString(5, student.getStudentId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                 // CONCEPT: Error Handling - Throwing custom exception
                 throw new StudentNotFoundException("Student with ID " + student.getStudentId() + " not found.");
            }
            System.out.println("Student updated successfully.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    @Override
    public void deleteStudent(String studentId) throws StudentNotFoundException {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new StudentNotFoundException("Student with ID " + studentId + " could not be deleted (not found).");
            }
            System.out.println("Student deleted.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    @Override
    public Student getStudent(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             pstmt.setString(1, studentId);
             ResultSet rs = pstmt.executeQuery();
             
             if (rs.next()) {
                 return new Student(
                     rs.getString("first_name"),
                     rs.getString("last_name"),
                     rs.getInt("age"),
                     rs.getString("student_id"),
                     rs.getDouble("grade")
                 );
             }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(new Student(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("age"),
                    rs.getString("student_id"),
                    rs.getDouble("grade")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return students;
    }
}
