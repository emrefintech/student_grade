package com.example.studentgrade;

import java.util.List;

public class DeleteBase extends BaseOperation{
    @Override
    public void performOperation() {
        // DeleteBase için operasyonun gerçekleştirilme detayları
    }

    @Override
    public void updateStudentGrade(Student oldStudent, Student newStudent) {

    }

    public static void deleteAllStudents(GradeBase gradeBase) {
        List<Student> students = gradeBase.getStudents();
        students.clear();
    }
}
