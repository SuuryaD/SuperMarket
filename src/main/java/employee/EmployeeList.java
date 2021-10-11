package employee;

import org.jetbrains.annotations.Nullable;
import util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Static class that Contains the list of all employees.
 */
public class EmployeeList {

    private static final List<Employee> employeeList = new ArrayList<>();

    /**
     * Authenticates the user
     * @param username username of employee
     * @param pass password of an employee
     * @return true if the credentials match, else false.
     */
    public static boolean authenticate(String username, String pass){

        for(Employee emp : employeeList){

            if(emp.getUsername().equals(username) && emp.getPassword().equals(pass))
                return true;
        }
        return false;
    }

    /**
     * Returns the employee object with the given username.
     * @param username username of the employee
     * @return employee object if username matches, else returns null.
     */
    public static @Nullable Employee getEmployee(String username){

        for(Employee emp : employeeList){
            if(emp.getUsername().equals(username))
                return emp;
        }
        return null;
    }

    public static void addEmployee(String name, String username, String pass, boolean isAdmin){

        employeeList.add(new Employee(name, username, pass, isAdmin));

    }

    /**
     * Checks if a username is available or taken.
     * @param username string to be checked
     * @return true if username is available, else returns false.
     */
    public static boolean checkUsernameAvailability(String username) {
        for(Employee emp : employeeList){
            if(emp.getUsername().equals(username))
                return false;
        }
        return true;
    }

    /**
     * Displays all the employees details in a tabular form.
     */
    public static void displayAll(){
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Employee Id");
        headers.add("Employee Name");
        headers.add("Role");

        ArrayList<ArrayList<String>> content = new ArrayList<>();
        for(Employee emp : employeeList){

            content.add(new ArrayList<>(Arrays.asList(
                    String.valueOf(emp.getId()),
                    emp.getName(),
                    String.valueOf(emp.isAdmin())
            )));

        }
        System.out.println(Globals.printTable(headers, content));
    }

    /**
     * Removes an employee from the list of employees.
     * @param id id of the employee to be removed.
     * @return true if the employee is removed
     */
    public static boolean removeEmployee(int id){

        for(Employee emp: employeeList){
            if(emp.getId() == id){
                employeeList.remove(emp);
                return true;
            }

        }
        return false;
    }

}
