package com.example.studentgrade;

import java.util.List;

public class UpdateBase extends BaseOperation {
    private GradeBase gradeBase;
    private StudentGrade studentGrade;

    public UpdateBase(GradeBase gradeBase, StudentGrade studentGrade) {
        this.gradeBase = gradeBase;
        this.studentGrade = studentGrade;
    }

    @Override
    public void performOperation() {

    }

    // Implementing the updateStudentGrade method inherited from the BaseOperation class
    @Override
    public void updateStudentGrade(Student oldStudent, Student newStudent) {
        List<Student> students = gradeBase.getStudents();
        if (students.contains(oldStudent)) {
            gradeBase.updateStudent(oldStudent, newStudent);
            System.out.println("Student information has been successfully updated.");
        } else {
            System.out.println("Error: Student not found.");
        }
    }

}
