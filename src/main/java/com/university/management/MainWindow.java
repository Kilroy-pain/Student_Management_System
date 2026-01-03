package com.university.management;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {
    private final IDataService dataService;
    private final StudentTableModel tableModel;

    public MainWindow(IDataService dataService) {
        this.dataService = dataService;
        
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen

        // Data Setup
        dataService.initializeDatabase();
        List<Student> students = dataService.getAllStudents();
        tableModel = new StudentTableModel(students);

        // UI Components
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Grade");
        JButton deleteButton = new JButton("Delete Student");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> showAddStudentDialog());
        updateButton.addActionListener(e -> showUpdateGradeDialog());
        deleteButton.addActionListener(e -> showDeleteStudentDialog());
        refreshButton.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        tableModel.setStudents(dataService.getAllStudents());
    }

    private void showAddStudentDialog() {
        JTextField idField = new JTextField();
        JTextField fNameField = new JTextField();
        JTextField lNameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField gradeField = new JTextField();

        Object[] message = {
            "ID:", idField,
            "First Name:", fNameField,
            "Last Name:", lNameField,
            "Age:", ageField,
            "Grade:", gradeField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                String fName = fNameField.getText();
                String lName = lNameField.getText();
                int age = Integer.parseInt(ageField.getText());
                double grade = Double.parseDouble(gradeField.getText());

                Student s = new Student(fName, lName, age, id, grade);
                dataService.addStudent(s);
                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding student: " + ex.getMessage());
            }
        }
    }

    private void showUpdateGradeDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID to update grade:");
        if (id != null && !id.trim().isEmpty()) {
            Student s = dataService.getStudent(id);
            if (s != null) {
                String gradeStr = JOptionPane.showInputDialog(this, "Enter new grade for " + s.getFirstName() + ":", s.getGrade());
                if (gradeStr != null) {
                    try {
                        double newGrade = Double.parseDouble(gradeStr);
                        s.setGrade(newGrade);
                        dataService.updateStudent(s);
                        refreshTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid grade format.");
                    } catch (StudentNotFoundException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        }
    }

    private void showDeleteStudentDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID to delete:");
        if (id != null && !id.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete student " + id + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dataService.deleteStudent(id);
                    refreshTable();
                } catch (StudentNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        }
    }
}
