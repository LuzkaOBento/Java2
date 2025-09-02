package model;

import annotation.CsvColumn;

public class Enrollment {
    private Student student;
    private Course course; 
    @CsvColumn(name = "Progresso (%)")
    private double progress;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.progress = 0.0;
    }


    public Course getCourse() {
        return course;
    }

    @CsvColumn(name = "Aluno")
    public String getStudentName() {
        return student.getName();
    }
    
    @CsvColumn(name = "Curso")
    public String getCourseTitle() {
        return course.getTitle();
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student.getName() +
                ", course=" + course.getTitle() +
                ", progress=" + progress +
                "%}";
    }
}