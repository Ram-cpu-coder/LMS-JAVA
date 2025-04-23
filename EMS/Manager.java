package EMS;

public class Manager extends Employee {
    private double bonus;

    public Manager(int id, String name, String department, double baseSalary, double performanceRating) {
        super(id, name, department, baseSalary, performanceRating);
        this.bonus = 0;
    }

    @Override
    public double calculateSalary() {
        return (baseSalary + bonus) + (performanceRating * 1000);
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}