package employee;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

  private static EmployeeRepository instance;
  private final List<Employee> employeeList;

  public EmployeeRepository() {
    this.employeeList = new ArrayList<>();
    initializeRepo();
  }

  public static EmployeeRepository getInstance() {
    if (instance == null) instance = new EmployeeRepository();
    return instance;
  }

  public boolean addEmployee(String name, String username, String pass, boolean admin) {
    return employeeList.add(new Employee(name, username, pass, admin));
  }

  public Employee getEmployee(String username, String pass) {
    for (Employee emp : employeeList) {
      if (emp.getUsername().equals(username) && emp.getPassword().equals(pass)) return emp;
    }
    return null;
  }

  public boolean removeEmployee(int id) {
    return employeeList.removeIf(employee -> employee.getId() == id);
  }

  public Employee getEmployeeByUsername(String username) {
    for (Employee emp : employeeList) {
      if (emp.getUsername().equals(username)) return emp;
    }
    return null;
  }

  public Employee getEmployeeById(int id) {
    for (Employee emp : employeeList) {
      if (emp.getId() == id) return emp;
    }
    return null;
  }

  public List<Employee> getAllEmployees() {
    return employeeList;
  }

  private void initializeRepo() {
    employeeList.add(new Employee("surya", "admin", BCrypt.hashpw("Admin@1", BCrypt.gensalt(10)), true));
    employeeList.add(new Employee("dhanush", "user1", BCrypt.hashpw("User@1", BCrypt.gensalt(10)), false));
  }
}
