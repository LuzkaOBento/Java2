package repository;

import dataconfig.InitialData;
import java.util.List;
import java.util.Optional;
import model.Course;

public class CourseRepository {

    public Optional<Course> findByTitle(String title) {
        // Busca um curso no mapa e retorna um Optional
        return Optional.ofNullable(InitialData.coursesByTitle.get(title));
    }

    public List<Course> findAll() {
        // Retorna todos os cursos como uma lista
        return List.copyOf(InitialData.coursesByTitle.values());
    }
    
    public void save(Course course) {
        // Salva ou atualiza um curso no mapa
        InitialData.coursesByTitle.put(course.getTitle(), course);
    }
}
