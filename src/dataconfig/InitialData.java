package dataconfig;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import model.Admin;
import model.Course;
import model.CourseStatus;
import model.DifficultyLevel;
import model.Enrollment;
import model.Student;
import model.SubscriptionPlan;
import model.User;
import support.SupportTicket;

public final class InitialData {

    public static final Map<String, Course> coursesByTitle = new HashMap<>();
    public static final Map<String, User> usersByEmail = new HashMap<>();
    public static final Queue<SupportTicket> supportTickets = new LinkedList<>();

    private InitialData() {}

    public static void populateData() {
        System.out.println("Populando o sistema com dados iniciais");
        
        Course javaBasico = new Course("Java Basico", "Conceitos fundamentais da linguagem Java.", "João Silva", 20.0, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Course springBoot = new Course("Spring Boot Essencial", "Construindo APIs REST com Spring Boot.", "Maria Oliveira", 40.0, DifficultyLevel.INTERMEDIATE, CourseStatus.ACTIVE);
        Course microservices = new Course("Microservicos com Java", "Padrões e práticas de arquitetura.", "Carlos Pereira", 60.0, DifficultyLevel.ADVANCED, CourseStatus.ACTIVE);
        Course pythonBasico = new Course("Python para Iniciantes", "Aprenda a programar com Python.", "Ana Souza", 15.0, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Course devops = new Course("Introdução a DevOps", "Ferramentas e cultura de CI/CD.", "João Silva", 25.0, DifficultyLevel.INTERMEDIATE, CourseStatus.INACTIVE);

        coursesByTitle.put(javaBasico.getTitle(), javaBasico);
        coursesByTitle.put(springBoot.getTitle(), springBoot);
        coursesByTitle.put(microservices.getTitle(), microservices);
        coursesByTitle.put(pythonBasico.getTitle(), pythonBasico);
        coursesByTitle.put(devops.getTitle(), devops);

        Admin admin = new Admin("Admin Master", "admin@academiadev.com");
        Student studentBasic = new Student("Alice Aluna", "alice@email.com", SubscriptionPlan.BASIC);
        Student studentPremium = new Student("Bob Premium", "bob@email.com", SubscriptionPlan.PREMIUM);
        Student studentSemMatricula = new Student("Charlie Zero", "charlie@email.com", SubscriptionPlan.BASIC);

        usersByEmail.put(admin.getEmail(), admin);
        usersByEmail.put(studentBasic.getEmail(), studentBasic);
        usersByEmail.put(studentPremium.getEmail(), studentPremium);
        usersByEmail.put(studentSemMatricula.getEmail(), studentSemMatricula);
        
        studentBasic.addEnrollment(new Enrollment(studentBasic, javaBasico));
        studentBasic.addEnrollment(new Enrollment(studentBasic, pythonBasico));
        studentPremium.addEnrollment(new Enrollment(studentPremium, javaBasico));
        studentPremium.addEnrollment(new Enrollment(studentPremium, springBoot));
        studentPremium.addEnrollment(new Enrollment(studentPremium, microservices));
        
        supportTickets.add(new SupportTicket(studentBasic, "Problema com login", "Não consigo acessar a plataforma."));
        supportTickets.add(new SupportTicket(studentPremium, "Dúvida sobre o curso", "Onde encontro o material de apoio?"));
        
        System.out.println("Dados iniciais populados com sucesso!");
    }
}