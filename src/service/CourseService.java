package service;

import exceptions.EnrollmentException;
import java.util.List;
import java.util.Optional;
import model.Course;
import model.CourseStatus;
import repository.CourseRepository;

public class CourseService {

    private final CourseRepository courseRepository;

    // Injeção de dependência via construtor
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Encontra um curso pelo título
    public Optional<Course> findCourseByTitle(String title) {
        return courseRepository.findByTitle(title);
    }

    // Lista todos os cursos
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }
    
    // Altera o status de um curso
    public Course changeCourseStatus(String title, CourseStatus newStatus) throws EnrollmentException {
        Course course = courseRepository.findByTitle(title)
                .orElseThrow(() -> new EnrollmentException("Curso com o título '" + title + "' não encontrado."));
        
        course.setStatus(newStatus);
        courseRepository.save(course);
        return course;
    }
}