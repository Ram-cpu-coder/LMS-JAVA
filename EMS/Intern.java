package EMS;

public class Intern extends Employee {
    private String mentor;

    public Intern(int id, String name, String department, double baseSalary, double performanceRating, String mentor) {
        super(id, name, department, baseSalary, performanceRating);
        this.mentor = mentor;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + (performanceRating * 200);
    }

    public String getMentor() { return mentor; }
}