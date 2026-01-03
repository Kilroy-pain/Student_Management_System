package com.university.management;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "First Name", "Last Name", "Age", "Grade", "Type"};
    private List<Student> students;

    public StudentTableModel(List<Student> students) {
        this.students = students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        switch (columnIndex) {
            case 0: return student.getStudentId();
            case 1: return student.getFirstName();
            case 2: return student.getLastName();
            case 3: return student.getAge();
            case 4: return student.getGrade();
            case 5: return "Student";
            default: return null;
        }
    }
}
