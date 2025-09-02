package model;

import annotation.CsvColumn;

public class Course {
    @CsvColumn(name = "Título")
    private String title;
    @CsvColumn(name = "Descrição")
    private String description;
    @CsvColumn(name = "Instrutor")
    private String instructorName;
    @CsvColumn(name = "Carga Horária (h)")
    private double durationInHours;
    @CsvColumn(name = "Nível de Dificuldade")
    private DifficultyLevel difficultyLevel;
    @CsvColumn(name = "Status")
    private CourseStatus status;

    public Course(String title, String description, String instructorName, double durationInHours, DifficultyLevel difficultyLevel, CourseStatus status) {
        this.title = title;
        this.description = description;
        this.instructorName = instructorName;
        this.durationInHours = durationInHours;
        this.difficultyLevel = difficultyLevel;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public double getDurationInHours() {
        return durationInHours;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", instructorName='" + instructorName + '\'' +
                ", durationInHours=" + durationInHours +
                ", difficultyLevel=" + difficultyLevel +
                ", status=" + status +
                '}';
    }
}