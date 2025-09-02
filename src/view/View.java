package view;

public class View {
    public static void showWelcome() {
        System.out.println("Bem-vindo à AcademiaDev!");
    }

    public static void showLoginMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Login");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void showMainMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Consultar Catálogo de Cursos");
        System.out.println("2. Ver Matrículas (apenas alunos)");
        System.out.println("3. Matricular-se em um curso (apenas alunos)");
        System.out.println("4. Atualizar Progresso (apenas alunos)");
        System.out.println("5. Abrir Ticket de Suporte");
        System.out.println("6. Acessar Funções de Admin (apenas admins)");
        System.out.println("0. Logout");
        System.out.print("Escolha uma opção: ");
    }
    
    public static void showAdminMenu() {
        System.out.println("\n--- Menu de Administrador ---");
        System.out.println("1. Gerenciar Status de Cursos");
        System.out.println("2. Gerar Relatórios");
        System.out.println("3. Atender Próximo Ticket");
        System.out.println("4. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }
    
    public static void showReportsMenu() {
        System.out.println("\n--- Relatórios ---");
        System.out.println("1. Cursos por Nível de Dificuldade");
        System.out.println("2. Instrutores Únicos");
        System.out.println("3. Média Geral de Progresso");
        System.out.println("4. Aluno com Mais Matrículas");
        System.out.println("5. Voltar ao Menu Admin");
        System.out.print("Escolha uma opção: ");
    }
}