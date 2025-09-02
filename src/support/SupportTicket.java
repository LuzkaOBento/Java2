package support;

import annotation.CsvColumn;
import model.User;

public class SupportTicket {
    private User user;
    @CsvColumn(name = "Título do Ticket")
    private String title;
    @CsvColumn(name = "Mensagem")
    private String message;

    public SupportTicket(User user, String title, String message) {
        this.user = user;
        this.title = title;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    @CsvColumn(name = "Usuário")
    public String getUserName() {
        return user.getName();
    }
    
    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "user=" + user.getName() +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}