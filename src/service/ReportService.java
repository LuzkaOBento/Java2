package service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import model.Course;
import model.CourseStatus;
import model.Student;
import model.SubscriptionPlan;
import repository.CourseRepository;
import repository.UserRepository;

public class ReportService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public ReportService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // Relatório 1: Lista de cursos por nível de dificuldade, ordenada pelo título
    public Map<String, List<Course>> getCoursesByDifficultyLevel() {
        return courseRepository.findAll().stream()
            .sorted(Comparator.comparing(Course::getTitle))
            .collect(Collectors.groupingBy(c -> c.getDifficultyLevel().name()));
    }

    // Relatório 2: Relação de instrutores únicos que ministram cursos ativos
    public Set<String> getUniqueActiveInstructors() {
        return courseRepository.findAll().stream()
            .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
            .map(Course::getInstructorName)
            .collect(Collectors.toSet());
    }

    // Relatório 3: Agrupamento de alunos por plano de assinatura
    public Map<SubscriptionPlan, List<Student>> groupStudentsByPlan() {
        return userRepository.findByAllStudents().stream() // Supondo um método findByAllStudents no UserRepository
            .collect(Collectors.groupingBy(Student::getSubscriptionPlan));
    }

    // Relatório 4: Média geral de progresso
    public double getOverallAverageProgress() {
        // Coleta todos os alunos e, para cada um, acessa o stream de suas matrículas
        return userRepository.findByAllStudents().stream()
            .flatMap(student -> student.getEnrollments().stream())
            .mapToDouble(enrollment -> enrollment.getProgress())
            .average()
            .orElse(0.0); // Retorna 0.0 se não houver matrículas
    }
    
    // Relatório 5: Aluno com o maior número de matrículas ativas
    public Optional<Student> getStudentWithMostEnrollments() {
        return userRepository.findByAllStudents().stream()
            .max(Comparator.comparingLong(
                student -> student.getEnrollments().stream()
                    .filter(e -> e.getCourse().getStatus() == CourseStatus.ACTIVE)
                    .count()
            ));
    }
}