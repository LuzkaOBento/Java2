package service;

import exceptions.EnrollmentException;
import java.util.Optional;
import model.Course;
import model.CourseStatus;
import model.Enrollment;
import model.Student;
import model.SubscriptionPlan;
import repository.CourseRepository;
import repository.UserRepository;

public class EnrollmentService {
    
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EnrollmentService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public void enrollStudent(String studentEmail, String courseTitle) throws EnrollmentException {
        Student student = findStudent(studentEmail)
            .orElseThrow(() -> new EnrollmentException("Usuário não encontrado ou não é um estudante."));
        
        Course course = courseRepository.findByTitle(courseTitle)
            .orElseThrow(() -> new EnrollmentException("Curso não encontrado."));

        // Regra de Negócio 1: O curso deve estar ativo
        if (course.getStatus() == CourseStatus.INACTIVE) {
            throw new EnrollmentException("Não é possível se matricular em um curso inativo.");
        }

        // Regra de Negócio 2: O aluno não pode estar matriculado no mesmo curso
        boolean alreadyEnrolled = student.getEnrollments().stream()
            .anyMatch(enrollment -> enrollment.getCourse().getTitle().equals(courseTitle));
        if (alreadyEnrolled) {
            throw new EnrollmentException("O aluno já está matriculado neste curso.");
        }

        // Regra de Negócio 3: Verificação de plano (apenas para o plano Básico)
        if (student.getSubscriptionPlan() == SubscriptionPlan.BASIC) {
            long activeEnrollmentsCount = student.getEnrollments().stream()
                .filter(e -> e.getCourse().getStatus() == CourseStatus.ACTIVE)
                .count();

            if (activeEnrollmentsCount >= 3) {
                throw new EnrollmentException("O plano Básico permite no máximo 3 matrículas ativas.");
            }
        }

        // Se todas as regras forem satisfeitas, cria a matrícula
        Enrollment newEnrollment = new Enrollment(student, course);
        student.addEnrollment(newEnrollment);
        userRepository.save(student); // Salva o estudante com a nova matrícula
        System.out.println("Matrícula de '" + student.getName() + "' no curso '" + course.getTitle() + "' realizada com sucesso!");
    }

    public void updateProgress(String studentEmail, String courseTitle, double newProgress) throws EnrollmentException {
        if (newProgress < 0 || newProgress > 100) {
            throw new EnrollmentException("O progresso deve ser um valor entre 0 e 100.");
        }
        
        Student student = findStudent(studentEmail)
            .orElseThrow(() -> new EnrollmentException("Usuário não encontrado ou não é um estudante."));
        
        Enrollment enrollment = student.getEnrollments().stream()
            .filter(e -> e.getCourse().getTitle().equals(courseTitle))
            .findFirst()
            .orElseThrow(() -> new EnrollmentException("O aluno não está matriculado neste curso."));
        
        enrollment.setProgress(newProgress);
        System.out.println("Progresso atualizado para " + newProgress + "% no curso '" + courseTitle + "'.");
    }

    public void cancelEnrollment(String studentEmail, String courseTitle) throws EnrollmentException {
        Student student = findStudent(studentEmail)
            .orElseThrow(() -> new EnrollmentException("Usuário não encontrado ou não é um estudante."));
        
        Optional<Enrollment> enrollmentToRemove = student.getEnrollments().stream()
            .filter(e -> e.getCourse().getTitle().equals(courseTitle))
            .findFirst();
        
        if (enrollmentToRemove.isEmpty()) {
            throw new EnrollmentException("O aluno não está matriculado neste curso.");
        }

        student.getEnrollments().remove(enrollmentToRemove.get());
        System.out.println("Matrícula no curso '" + courseTitle + "' cancelada com sucesso.");
    }

    private Optional<Student> findStudent(String email) {
        return userRepository.findByEmail(email)
            .filter(user -> user instanceof Student)
            .map(user -> (Student) user);
    }
}