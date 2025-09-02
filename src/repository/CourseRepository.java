package repository;

import dataconfig.InitialData;
import java.util.List;
import java.util.Optional;
import model.Course;

public class CourseRepository {

    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(InitialData.coursesByTitle.get(title));
    }

    public List<Course> findAll() {
        return List.copyOf(InitialData.coursesByTitle.values());
    }
    
    public void save(Course course) {
        InitialData.coursesByTitle.put(course.getTitle(), course);
    }
}
