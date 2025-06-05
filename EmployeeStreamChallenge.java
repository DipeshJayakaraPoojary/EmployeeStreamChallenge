import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

public class EmployeeStreamChallenge {
    static class Employee {
        String name;
        String department;
        double salary;
        LocalDate startDate;
        List<String> skills;

        public Employee(String name, String department, double salary, LocalDate startDate, List<String> skills) {
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.startDate = startDate;
            this.skills = skills;
        }

        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
        public LocalDate getStartDate() { return startDate; }
        public List<String> getSkills() { return skills; }

        @Override
        public String toString() {
            return name + " (" + department + ", $" + salary + ")";
        }
    }

    // Sample data
    static List<Employee> getSampleEmployees() {
        return Arrays.asList(
            new Employee("Alice", "Engineering", 85000, LocalDate.of(2018, 3, 10), Arrays.asList("Java", "Spring")),
            new Employee("Bob", "HR", 55000, LocalDate.of(2016, 7, 1), Arrays.asList("Communication", "Recruitment")),
            new Employee("Charlie", "Engineering", 92000, LocalDate.of(2019, 1, 5), Arrays.asList("Java", "Docker")),
            new Employee("Diana", "Sales", 48000, LocalDate.of(2020, 5, 15), Arrays.asList("Negotiation", "CRM")),
            new Employee("Ethan", "Sales", 76000, LocalDate.of(2017, 9, 23), Arrays.asList("CRM", "Analytics")),
            new Employee("Fiona", "Marketing", 69000, LocalDate.of(2021, 2, 28), Arrays.asList("SEO", "Content")),
            new Employee("George", "Engineering", 105000, LocalDate.of(2015, 6, 30), Arrays.asList("Java", "Kubernetes", "AWS"))
        );
    }

    public static void main(String[] args) {
        List<Employee> employees = getSampleEmployees();

        System.out.println("Challenge Set A:");

        // Find employees earning more than $75,000
        employees.stream()
                 .filter(e -> e.getSalary() > 75000)
                 .forEach(System.out::println);

        // Unique department names alphabetically
        System.out.println("\nDepartments:");
        employees.stream()
                 .map(Employee::getDepartment)
                 .distinct()
                 .sorted()
                 .forEach(System.out::println);

        //Total salary expenditure
        double totalPayroll = employees.stream()
                                       .mapToDouble(Employee::getSalary)
                                       .sum();
        System.out.println("\nTotal Payroll: $" + totalPayroll);

        System.out.println("\nChallenge Set B:");

        // Avg salary per department
        System.out.println("Average Salary by Department:");
        employees.stream()
                 .collect(Collectors.groupingBy(Employee::getDepartment,
                         Collectors.averagingDouble(Employee::getSalary)))
                 .forEach((dept, avg) -> System.out.println(dept + ": $" + avg));

        // Unique sorted skills
        System.out.println("\nSkills Inventory:");
        employees.stream()
                 .flatMap(e -> e.getSkills().stream())
                 .distinct()
                 .sorted()
                 .forEach(System.out::println);

        // Top 3 Highest Paid Employees
        System.out.println("\nTop 3 Earners:");
        employees.stream()
                 .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                 .limit(3)
                 .map(Employee::getName)
                 .forEach(System.out::println);

        System.out.println("\nChallenge Set C:");

        
        System.out.println("Department Report:");
        employees.stream()
                 .collect(Collectors.groupingBy(Employee::getDepartment,
                         Collectors.mapping(Employee::getName, Collectors.joining(", "))))
                 .forEach((dept, names) -> System.out.println(dept + ": " + names));

        // 8. Salary Bands
        System.out.println("\nSalary Bands:");
        employees.stream()
                 .collect(Collectors.groupingBy(e -> {
                     if (e.getSalary() <= 50000) return "0-50k";
                     else if (e.getSalary() <= 80000) return "50k-80k";
                     else return "80k+";
                 }))
                 .forEach((band, emps) -> {
                     System.out.println(band + ": " + emps.stream().map(Employee::getName).collect(Collectors.joining(", ")));
                 });

        // Highest paid employee per department
        System.out.println("\nTop Talent Per Department:");
        employees.stream()
                 .collect(Collectors.groupingBy(Employee::getDepartment,
                         Collectors.collectingAndThen(
                             Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)),
                             opt -> opt.map(Employee::getName).orElse("None")
                         )))
                 .forEach((dept, name) -> System.out.println(dept + ": " + name));
    }
}
