package repository;

import dataconfig.InitialData;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.Student;
import model.User;

public class UserRepository {
    
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(InitialData.usersByEmail.get(email));
    }

    public User save(User user) {

        InitialData.usersByEmail.put(user.getEmail(), user);
        return user;
    }
    public List<Student> findByAllStudents() {
    return InitialData.usersByEmail.values().stream()
        .filter(user -> user instanceof Student)
        .map(user -> (Student) user)
        .collect(Collectors.toList());
}
}