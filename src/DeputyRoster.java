import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
public class DeputyRoster {

    public static void main(String[] args) {
        // Define the shifts
        String[] shifts = {
                "05:00-10:00",
                "5:00-11:00",
                "6:00-12:00",
                "10:00-15:00",
                "11:00-16:00",
                "12:00-17:00",
                "15:00-19:00",
                "17:00-22:00"
        };

        // Get the names of the employees from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the names of the employees, separated by commas: ");
        String[] names = scanner.nextLine().split(",");

        // Generate the schedule
        Schedule schedule = new Schedule(names, shifts);
        schedule.generate();

        // Print the schedule
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/y");
        Calendar calendar = Calendar.getInstance();
        for (Employee employee : schedule.employees) {
            System.out.println(employee.name + ":");
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            for (Shift shift : employee.shifts) {
                System.out.println("  " + dateFormat.format(calendar.getTime()) + ": " + shift.time);
                calendar.add(Calendar.DATE, 1);
            }
            System.out.println();
        }
    }

}

class Employee {
    public String name;
    public ArrayList<Shift> shifts = new ArrayList<>();

    public Employee(String name) {
        this.name = name;
    }
}

class Shift {
    public String time;
    public String task;

    public Shift(String time) {
        this.time = time;
        this.task = task;
    }
}

class Schedule {

    // The list of employees
    public ArrayList<Employee> employees = new ArrayList<>();

    // The list of shifts
    public String[] shifts;

    // The total number of hours in the schedule
    public int totalHours;

    // The minimum and maximum number of hours per week for each employee
    public int minHoursPerWeek = 20;
    public int maxHoursPerWeek = 30;

    // The constructor takes the names of the employees and the shifts
    public Schedule(String[] names, String[] shifts) {
        // Create an employee object for each name
        for (String name : names) {
            employees.add(new Employee(name));
        }
        this.shifts = shifts;
    }

//    // Generate the schedule
//    public void generate() {
//        // Calculate the total number of hours in the schedule
//        for (String shift : shifts) {
//            String[] parts = shift.split("-");
//            totalHours += Integer.parseInt(parts[1].split(":")[0]) - Integer.parseInt(parts[0].split(":")[0]);
//        }
//
//        // Create a two-dimensional array to store the schedule for each day and employee
//        Employee[][] schedule = new Employee[7][shifts.length];
//
//        // Assign shifts to employees for each day of the week
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//        for (int day = 0; day < 7; day++) {
//            // Keep track of the total number of hours left to be assigned
//            int hoursLeft = totalHours;
//
//            // Assign shifts to employees until the schedule is full
//            int i = 0;
//            while (hoursLeft > 0) {
//                Employee employee = employees.get(i);
//                int hoursPerWeek = minHoursPerWeek + (int)(Math.random() * (maxHoursPerWeek - minHoursPerWeek + 1));
//                if (employee.shifts.size() + hoursPerWeek > totalHours) {
//                    hoursPerWeek = totalHours - employee.shifts.size();
//                }
//                hoursLeft -= hoursPerWeek;
//
//                // Add shifts to the employee's schedule
//                for (int j = 0; j < shifts.length; j++) {
//                    String shift = shifts[j];
//                    String[] parts = shift.split("-");
//                    int startTime = Integer.parseInt(parts[0].split(":")[0]);
//                    int endTime = Integer.parseInt(parts[1].split(":")[0]);
//                    int duration = endTime - startTime;
//                    if (duration <= hoursPerWeek) {
//                        hoursPerWeek -= duration;
//                        employee.shifts.add(new Shift(shift));
//                        schedule[day][j] = employee;
//                    }
//                }
//
//                i++;
//                if (i >= employees.size()) {
//                    i = 0;
//                }
//            }
//
//            calendar.add(Calendar.DATE, 1);
//        }
//        }

    // Generate the schedule
    public void generate() {
        // Calculate the total number of hours in the schedule
        for (String shift : shifts) {
            String[] parts = shift.split("-");
            totalHours += Integer.parseInt(parts[1].split(":")[0]) - Integer.parseInt(parts[0].split(":")[0]);
        }

        // Create a two-dimensional array to store the schedule for each day and employee
        Employee[][] schedule = new Employee[7][shifts.length];

        // Assign shifts to employees for each day of the week
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for (int day = 0; day < 7; day++) {
            // Keep track of the total number of hours left to be assigned
            int hoursLeft = totalHours;

            // Assign shifts to employees in a round-robin fashion until the schedule is full
            int i = 0;
            while (hoursLeft > 0) {
                Employee employee = employees.get(i);
                // Assign the current shift to the employee
                employee.shifts.add(new Shift(shifts[day]));
                hoursLeft--;
                i++;
                if (i == employees.size()) {
                    i = 0;
                }
            }
        }

        // Print the schedule
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/y");
        calendar = Calendar.getInstance();
        for (Employee employee : employees) {
            System.out.println(employee.name + ":");
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            for (Shift shift : employee.shifts) {
                System.out.println("  " + dateFormat.format(calendar.getTime()) + ": " + shift.time);
                calendar.add(Calendar.DATE, 1);
            }
            System.out.println();
        }
    }



    }