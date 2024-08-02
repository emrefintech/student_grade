package com.example.studentgrade;

import java.util.ArrayList;
import java.util.List;

public class GradeBase {
    private List<Student> students;


    public GradeBase() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void updateStudent(Student oldStudent, Student newStudent) {
        int index = students.indexOf(oldStudent);
        if (index != -1) {
            students.set(index, newStudent); // Update the relevant position of the list with the new student
        }
    }


    public void showStudentsInConsole() {
        List<Student> students = this.getStudents();
        System.out.println("Student information:");
        for (Student student : students) {
            System.out.println("Name: " + student.getName() + " Student Number: " + student.getNumber() + " Grade: " + student.getGrade() + " Attending: " + student.isAttending() + " Gender: " + student.getGender());
        }
    }


    public List<Student> getStudents() {
        return students;
    }
}
