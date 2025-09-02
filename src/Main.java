import controller.AppController;
import dataconfig.InitialData;
import repository.CourseRepository;
import repository.UserRepository;

public class Main {
    public static void main(String[] args) {

        InitialData.populateData();
        
        CourseRepository courseRepository = new CourseRepository();
        UserRepository userRepository = new UserRepository();

        AppController app = new AppController(courseRepository, userRepository);
        app.start();
    }
}