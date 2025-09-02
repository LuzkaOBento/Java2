package dto;

public class StudentProgressDTO {
    private String courseTitle;
    private double progress;

    public StudentProgressDTO(String courseTitle, double progress) {
        this.courseTitle = courseTitle;
        this.progress = progress;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public double getProgress() {
        return progress;
    }
}