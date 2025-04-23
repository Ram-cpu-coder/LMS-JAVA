package EMS;

import java.io.Serializable;

public abstract class Employee implements Serializable {
    protected int id;
    protected String name;
    protected String department;
    protected double baseSalary;
    protected double performanceRating;
    protected String status;

    public Employee(int id, String name, String department, double baseSalary, double performanceRating) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
        this.performanceRating = performanceRating;
        this.status = "Active";
    }

    public abstract double calculateSalary();

    // Common methods for all employees
    public void updatePerformance(double newRating) {
        this.performanceRating = Math.max(0, Math.min(5, newRating));
    }

    public void applyBonus(double amount) {
        this.baseSalary += amount;
    }

    public void applyFine(double amount) {
        this.baseSalary -= amount;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getBaseSalary() { return baseSalary; }
    public double getPerformanceRating() { return performanceRating; }
    public String getStatus() { return status; }
}