package model;

public class Admin extends User {
    public Admin(String name, String email) {
        super(name, email);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
    
    @Override
    public String toString() {
        return "Admin{" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}