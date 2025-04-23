package EMS;

import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;

public class Main {
    private static EMS ems = new EMS();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeSystem();
        mainMenu();
    }

    private static void initializeSystem() {
        System.out.println("Initializing Employee Management System...");
        try {
            ems.loadFromFile("employees.csv");
            System.out.println("Successfully loaded employee records!");
        } catch (IOException e) {
            System.out.println("Warning: No existing data file found. Starting with empty database.");
        }
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Add New Employee");
            System.out.println("2. Update Employee Record");
            System.out.println("3. Delete Employee");
            System.out.println("4. View All Employees");
            System.out.println("5. Search Employees");
            System.out.println("6. Save and Exit");
            System.out.print("Enter your choice (1-6): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addEmployeeFlow();
                        break;
                    case 2:
                        updateEmployeeFlow();
                        break;
                    case 3:
                        deleteEmployeeFlow();
                        break;
                    case 4:
                        displayAllEmployees();
                        break;
                    case 5:
                        searchEmployeesFlow();
                        break;
                    case 6:
                        exitProcedure();
                        return;
                    default:
                        System.out.println("Invalid choice! Please enter 1-6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // ADD EMPLOYEE FLOW
    private static void addEmployeeFlow() {
        System.out.println("\n--- Add New Employee ---");
        System.out.print("Enter employee type (Manager/Regular/Intern): ");
        String type = scanner.nextLine();

        // Get ID
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline

        // Get Name
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        // Get Department
        System.out.print("Department: ");
        String dept = scanner.nextLine().trim();

        // Get Salary
        System.out.print("Base Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume leftover newline

        // Get Performance Rating
        System.out.print("Performance Rating (0-5): ");
        double rating = scanner.nextDouble();
        scanner.nextLine(); // Consume leftover newline

        Employee emp = createEmployee(type, id, name, dept, salary, rating);
        if (emp != null) {
            ems.addEmployee(emp);
            System.out.println("\nSuccess: Employee added!");
            System.out.printf("%s %s (ID: %d) created.%n", type, name, id);
        }
    }

    // UPDATE EMPLOYEE FLOW
    private static void updateEmployeeFlow() {
        System.out.println("\n--- Update Employee ---");
        int id = getValidInt("Enter employee ID to update: ");
        Employee emp = ems.findEmployee(id);

        if (emp == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.println("1. Update Performance Rating");
        System.out.println("2. Apply Bonus");
        System.out.println("3. Apply Fine");
        System.out.println("4. Update Status");
        System.out.print("Choose update type: ");

        int choice = getValidInt("");
        switch (choice) {
            case 1:
                double rating = getValidRating("New performance rating: ");
                emp.updatePerformance(rating);
                System.out.println("Performance updated!");
                break;
            case 2:
                double bonus = getValidDouble("Bonus amount: ");
                emp.applyBonus(bonus);
                System.out.println("Bonus applied!");
                break;
            case 3:
                double fine = getValidDouble("Fine amount: ");
                emp.applyFine(fine);
                System.out.println("Fine applied!");
                break;
            case 4:
                String status = getValidString("New status: ");
                emp.updateStatus(status);
                System.out.println("Status updated!");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // DELETE EMPLOYEE FLOW
    private static void deleteEmployeeFlow() {
        System.out.println("\n--- Delete Employee ---");
        int id = getValidInt("Enter employee ID to delete: ");
        ems.deleteEmployee(id);
        System.out.println("Employee deleted (if existed)");
    }

    // DISPLAY ALL EMPLOYEES
    private static void displayAllEmployees() {
        System.out.println("\n--- All Employees ---");
        ems.displayAll();
    }

    // SEARCH EMPLOYEES FLOW
    private static void searchEmployeesFlow() {
        System.out.println("\n--- Search Employees ---");
        String query = getValidString("Enter search query (name, ID, or performance): ");
        List<Employee> results = ems.queryEmployees(query);

        if (results.isEmpty()) {
            System.out.println("No matching employees found.");
        } else {
            System.out.println("\nSearch Results:");
            for (Employee emp : results) {
                System.out.printf("ID: %d | Name: %-15s | Dept: %-10s | Salary: $%,.2f%n",
                        emp.getId(), emp.getName(), emp.getDepartment(), emp.calculateSalary());
            }
        }
    }

    // EXIT PROCEDURE
    private static void exitProcedure() {
        System.out.println("\nSaving data...");
        try {
            ems.saveToFile("employees.csv");
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
        System.out.println("Exiting system. Goodbye!");
    }

    // VALIDATION HELPERS
    private static int getValidInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a whole number.");
                scanner.nextLine();
            }
        }
    }

    private static double getValidDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static String getValidString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static double getValidRating(String prompt) {
        while (true) {
            double rating = getValidDouble(prompt);
            if (rating >= 0 && rating <= 5)
                return rating;
            System.out.println("Rating must be between 0 and 5!");
        }
    }

    private static Employee createEmployee(String type, int id, String name, String dept, double salary,
            double rating) {
        switch (type.toLowerCase()) {
            case "manager":
                return new Manager(id, name, dept, salary, rating);
            case "regular":
                return new RegularEmployee(id, name, dept, salary, rating);
            case "intern":
                String mentor = getValidString("Mentor: ");
                return new Intern(id, name, dept, salary, rating, mentor);
            default:
                System.out.println("Invalid employee type!");
                return null;
        }
    }
}