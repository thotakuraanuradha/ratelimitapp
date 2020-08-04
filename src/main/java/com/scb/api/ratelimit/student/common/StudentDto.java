package com.scb.api.ratelimit.student.common;

public class StudentDto {

    private int studentId;
    private String studentName;
    private double studentMarks;

    public StudentDto(int studentId, String studentName, double studentMarks) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentMarks = studentMarks;
    }

    public StudentDto() {
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(double studentMarks) {
        this.studentMarks = studentMarks;
    }
}
