package employee;

import org.mindrot.jbcrypt.BCrypt;
import util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Static class that contains the list of all employees. */
public class EmployeeList {

  private static EmployeeList instance;
  private final List<Employee> employeeList = new ArrayList<>();

  public static EmployeeList getInstance() {
    if (instance == null) {
      instance = new EmployeeList();
    }
    return instance;
  }

  /**
   * Authenticates the user
   *
   * @param username username of employee
   * @param pass password of an employee
   * @return true if the credentials match, else false.
   */
  public Employee authenticate(String username, String pass) {

    for (Employee emp : employeeList) {

      if (emp.getUsername().equals(username) && BCrypt.checkpw(pass, emp.getPassword())) return emp;
    }
    return null;
  }

//  public void addEmployee(String name, String username, String pass, boolean isAdmin) {
//
//    employeeList.add(new Employee(name, username, pass, isAdmin));
//  }

  /**
   * Checks if a username is available or taken.
   *
   * @param username string to be checked
   * @return true if username is available, else returns false.
   */
  public boolean isUsernameAvailable(String username) {
    for (Employee emp : employeeList) {
      if (emp.getUsername().equals(username)) return false;
    }
    return true;
  }

  /** Displays all the employees details in a tabular form. */
  @Override
  public String toString() {
    ArrayList<String> headers = new ArrayList<>();
    headers.add("Employee Id");
    headers.add("Employee Name");
    headers.add("Role");

    ArrayList<ArrayList<String>> content = new ArrayList<>();
    for (Employee emp : EmployeeRepository.getAll()) {

      content.add(
          new ArrayList<>(
              Arrays.asList(
                  String.valueOf(emp.getId()),
                  emp.getName(),
                  emp.isAdmin() ? "ADMIN" : "CASHIER")));
    }
    return Globals.printTable(headers, content);
  }

  /**
   * Removes an employee from the list of employees.
   *
   * @param id id of the employee to be removed.
   * @return true if the employee is removed
   */
  public boolean removeEmployee(int id) {

    for (Employee emp : employeeList) {
      if (emp.getId() == id) {
        employeeList.remove(emp);
        return true;
      }
    }
    return false;
  }
}
