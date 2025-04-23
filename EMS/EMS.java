package EMS;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EMS {
    private List<Employee> employees;
    private String currentFile;

    public EMS() {
        employees = new ArrayList<>();
        currentFile = "/Users/nabinshiwakoti/eclipse-workspace/lab/src/EMS/employees_data.csv";
    }

    public void loadFromFile(String filename) throws IOException {
        System.out.println("Saving to: " + new File(filename).getAbsolutePath());
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String department = data[2];
                double baseSalary = Double.parseDouble(data[3]);
                double performance = Double.parseDouble(data[4]);
                String type = data[5];

                switch (type) {
                    case "Manager":
                        employees.add(new Manager(id, name, department, baseSalary, performance));
                        break;
                    case "Regular":
                        employees.add(new RegularEmployee(id, name, department, baseSalary, performance));
                        break;
                    case "Intern":
                        String mentor = data[6];
                        employees.add(new Intern(id, name, department, baseSalary, performance, mentor));
                        break;
                }
            }
            currentFile = filename;
        }
    }

    public void saveToFile(String filename) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Employee emp : employees) {
                String type = "";
                String extra = "";

                if (emp instanceof Manager) {
                    type = "Manager";
                } else if (emp instanceof RegularEmployee) {
                    type = "Regular";
                } else if (emp instanceof Intern) {
                    type = "Intern";
                    extra = "," + ((Intern) emp).getMentor();
                }

                pw.println(
                        emp.getId() + "," +
                                emp.getName() + "," + // Ensure name is included
                                emp.getDepartment() + "," +
                                emp.calculateSalary() + "," +
                                emp.getPerformanceRating() + "," +
                                type + extra);
            }
        }
    }

    public void addEmployee(Employee emp) {
        employees.add(emp);
    }

    public void deleteEmployee(int id) {
        employees.removeIf(emp -> emp.getId() == id);
    }

    public Employee findEmployee(int id) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null;
    }

    public List<Employee> queryEmployees(String query) {
        List<Employee> results = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getName().contains(query) ||
                    String.valueOf(emp.getId()).contains(query) ||
                    String.valueOf(emp.getPerformanceRating()).contains(query)) {
                results.add(emp);
            }
        }
        return results;
    }

    public void displayAll() {
        for (Employee emp : employees) {
            System.out.printf("ID: %d | Name: %-15s | Dept: %-10s | Salary: $%,.2f | Performance: %.1f%n",
                    emp.getId(), emp.getName(), emp.getDepartment(), emp.calculateSalary(), emp.getPerformanceRating());
        }
    }
}
