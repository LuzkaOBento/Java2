package controller;

import model.CourseStatus;
import model.SubscriptionPlan;
import exceptions.EnrollmentException;
import exceptions.CourseNotFoundException;
import exceptions.UserNotFoundException;
import model.Course;
import model.Student;
import support.SupportTicket;
import model.User;
import repository.CourseRepository;
import repository.UserRepository;
import service.CourseService;
import service.EnrollmentService;
import service.ReportService;
import service.UserService;
import util.GenericCsvExporter;
import view.View;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import dataconfig.InitialData;

public class AppController {

    private final Scanner scanner = new Scanner(System.in);

    // Injeção de dependências
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseService courseService;
    private final UserService userService;
    private final EnrollmentService enrollmentService;
    private final ReportService reportService;

    // Estado da aplicação
    private Optional<User> loggedInUser = Optional.empty();

    public AppController(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseService = new CourseService(this.courseRepository);
        this.userService = new UserService(this.userRepository);
        this.enrollmentService = new EnrollmentService(this.courseRepository, this.userRepository);
        this.reportService = new ReportService(this.courseRepository, this.userRepository);
    }

    public void start() {
        View.showWelcome();
        while (true) {
            if (loggedInUser.isEmpty()) {
                handleLoginMenu();
            } else {
                handleMainMenu();
            }
        }
    }

    private void handleLoginMenu() {
        View.showLoginMenu();
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 0:
                    System.out.println("Até logo!");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine();
        }
    }

    private void handleLogin() {
        System.out.print("Digite seu email para login: ");
        String email = scanner.nextLine();
        try {
            loggedInUser = userService.findUserByEmail(email);
            System.out.println("Login bem-sucedido. Olá, " + loggedInUser.get().getName() + "!");
        } catch (UserNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void handleMainMenu() {
        View.showMainMenu();
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    listAllCourses();
                    break;
                case 2:
                    if (loggedInUser.get() instanceof Student) {
                        listStudentEnrollments();
                    } else {
                        System.out.println("Opção indisponível para administradores.");
                    }
                    break;
                case 3:
                    if (loggedInUser.get() instanceof Student) {
                        enrollStudentInCourse();
                    } else {
                        System.out.println("Opção indisponível para administradores.");
                    }
                    break;
                case 4:
                    if (loggedInUser.get() instanceof Student) {
                        updateStudentProgress();
                    } else {
                        System.out.println("Opção indisponível para administradores.");
                    }
                    break;
                case 5:
                    openSupportTicket();
                    break;
                case 6:
                    if (loggedInUser.get().getRole().equals("Admin")) {
                        handleAdminMenu();
                    } else {
                        System.out.println("Permissão negada. Acesso apenas para administradores.");
                    }
                    break;
                case 0:
                    loggedInUser = Optional.empty();
                    System.out.println("Logout realizado com sucesso.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine();
        }
    }

    private void listAllCourses() {
        System.out.println("\n--- Catálogo de Cursos Ativos ---");
        courseService.findAllCourses().stream()
                .filter(course -> course.getStatus().name().equals("ACTIVE"))
                .forEach(System.out::println);
    }

    private void listStudentEnrollments() {
        Student student = (Student) loggedInUser.get();
        System.out.println("\n--- Minhas Matrículas ---");
        if (student.getEnrollments().isEmpty()) {
            System.out.println("Você não está matriculado em nenhum curso.");
        } else {
            student.getEnrollments().forEach(System.out::println);
        }
    }

    private void enrollStudentInCourse() {
        System.out.print("Digite o título do curso para se matricular: ");
        String title = scanner.nextLine();
        try {
            enrollmentService.enrollStudent(loggedInUser.get().getEmail(), title);
        } catch (EnrollmentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void updateStudentProgress() {
        System.out.print("Digite o título do curso: ");
        String title = scanner.nextLine();
        System.out.print("Digite o novo progresso (0-100): ");
        try {
            double progress = scanner.nextDouble();
            scanner.nextLine();
            enrollmentService.updateProgress(loggedInUser.get().getEmail(), title, progress);
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine();
        } catch (EnrollmentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void openSupportTicket() {
        System.out.print("Digite o título do ticket: ");
        String title = scanner.nextLine();
        System.out.print("Digite a mensagem do ticket: ");
        String message = scanner.nextLine();
        SupportTicket newTicket = new SupportTicket(loggedInUser.get(), title, message);
        InitialData.supportTickets.add(newTicket);
        System.out.println("Ticket criado. Número do ticket: " + (InitialData.supportTickets.size()));
    }

    private void handleAdminMenu() {
        View.showAdminMenu();
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    manageCourseStatus();
                    break;
                case 2:
                    handleReportsMenu();
                    break;
                case 3:
                    if (InitialData.supportTickets.isEmpty()) {
                        System.out.println("Não há tickets na fila.");
                    } else {
                        SupportTicket nextTicket = InitialData.supportTickets.poll();
                        System.out.println("Atendendo ticket de " + nextTicket.getUser().getName() + ": " + nextTicket.getTitle());
                        System.out.println("Mensagem: " + nextTicket.getMessage());
                    }
                    break;
                case 4:
                    exportDataToCsv();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine();
        }
    }

    private void manageCourseStatus() {
        System.out.print("Digite o título do curso: ");
        String title = scanner.nextLine();
        System.out.print("Digite o novo status (ACTIVE/INACTIVE): ");
        String statusStr = scanner.nextLine().toUpperCase();
        try {
            courseService.changeCourseStatus(title, CourseStatus.valueOf(statusStr));
            System.out.println("Status do curso atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void handleReportsMenu() {
        View.showReportsMenu();
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("\n--- Relatório de Cursos por Nível ---");
                    reportService.getCoursesByDifficultyLevel().forEach((key, value) -> {
                        System.out.println(">> Nível: " + key);
                        value.forEach(course -> System.out.println("- " + course.getTitle()));
                    });
                    break;
                case 2:
                    System.out.println("\n--- Relatório de Instrutores Únicos ---");
                    reportService.getUniqueActiveInstructors().forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("\n--- Média Geral de Progresso ---");
                    System.out.printf("Média de Progresso: %.2f%%\n", reportService.getOverallAverageProgress());
                    break;
                case 4:
                    System.out.println("\n--- Aluno com Mais Matrículas ---");
                    Optional<Student> studentWithMost = reportService.getStudentWithMostEnrollments();
                    studentWithMost.ifPresentOrElse(
                        student -> System.out.println("Aluno: " + student.getName() + " com " + student.getEnrollments().size() + " matrículas."),
                        () -> System.out.println("Nenhum aluno encontrado.")
                    );
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine();
        }
    }

    private void exportDataToCsv() {
        try {
            System.out.print("Digite o tipo de dados para exportar (cursos, alunos): ");
            String dataType = scanner.nextLine().toLowerCase();

            String csvOutput = "";
            switch (dataType) {
                case "cursos":
                    List<Course> courses = courseRepository.findAll();
                    csvOutput = GenericCsvExporter.exportToCsv(courses, Course.class);
                    break;
                case "alunos":
                    List<Student> students = userRepository.findByAllStudents();
                    csvOutput = GenericCsvExporter.exportToCsv(students, Student.class);
                    break;
                default:
                    System.out.println("Tipo de dado inválido.");
                    return;
            }
            System.out.println("\n--- CSV Gerado ---");
            System.out.println(csvOutput);

        } catch (Exception e) {
            System.out.println("Erro ao exportar dados: " + e.getMessage());
        }
    }
}