package model;

import annotation.CsvColumn;
import java.util.List;

public class Student extends User {
    @CsvColumn(name = "Plano de Assinatura")
    private SubscriptionPlan subscriptionPlan;
    private List<Enrollment> enrollments; // Não anotado, pois a lista de matrículas não é exportada em CSV de forma direta.

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        this.subscriptionPlan = subscriptionPlan;
        this.enrollments = new java.util.ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
    
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", subscriptionPlan=" + subscriptionPlan +
                '}';
    }
}