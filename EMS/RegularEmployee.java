package EMS;

public class RegularEmployee extends Employee {
    private static final double HOURLY_RATE = 25.0;
    private static final int STANDARD_HOURS = 160;

    public RegularEmployee(int id, String name, String department, double baseSalary, double performanceRating) {
        super(id, name, department, baseSalary, performanceRating);
    }

    @Override
    public double calculateSalary() {
        return baseSalary + (HOURLY_RATE * STANDARD_HOURS) + (performanceRating * 500);
    }
}
