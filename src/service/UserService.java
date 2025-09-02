package service;

import exceptions.EnrollmentException;
import java.util.Optional;
import model.Student;
import model.SubscriptionPlan;
import model.User;
import repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    // Injeção de dependência via construtor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // Método para buscar um usuário pelo email de forma segura
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Altera o plano de assinatura de um aluno
    public Student changeStudentPlan(String email, SubscriptionPlan newPlan) throws EnrollmentException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EnrollmentException("Usuário com o email " + email + " não encontrado."));

        if (!(user instanceof Student)) {
            throw new EnrollmentException("Apenas estudantes podem ter planos de assinatura.");
        }

        Student student = (Student) user;
        student.setSubscriptionPlan(newPlan);
        return (Student) userRepository.save(student);
    }
}