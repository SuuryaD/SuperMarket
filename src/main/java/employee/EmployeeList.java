package employee;

import org.jetbrains.annotations.Nullable;
import util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeList {

    private static final List<Employee> employeeList = new ArrayList<>();

    public static boolean authenticate(String username, String pass){

        for(Employee emp : employeeList){

            if(emp.getUsername().equals(username) && emp.getPassword().equals(pass))
                return true;
        }
        return false;
    }

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

    public static boolean checkUsernameAvailability(String username) {
        for(Employee emp : employeeList){
            if(emp.getUsername().equals(username))
                return false;
        }
        return true;
    }

    public static void displayAll(){
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Employee Id");
        headers.add("Employee Name");
        headers.add("Role");

        ArrayList<ArrayList<String>> content = new ArrayList<>();
        for(Employee emp : employeeList){

            content.add(new ArrayList<String>(Arrays.asList(
                    String.valueOf(emp.getId()),
                    emp.getName(),
                    String.valueOf(emp.isAdmin())
            )));

        }
        Globals.printTable(headers, content);
    }

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
