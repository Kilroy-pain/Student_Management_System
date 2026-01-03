package com.university.management;

import java.util.List;

// CONCEPT: Abstraction - Interface
public interface IDataService {
    void addStudent(Student student);
    void updateStudent(Student student) throws StudentNotFoundException;
    void deleteStudent(String studentId) throws StudentNotFoundException;
    Student getStudent(String studentId);

    List<Student> getAllStudents();
    
     void initializeDatabase();
}
