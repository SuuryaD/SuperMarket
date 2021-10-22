package sdk.employee;

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

  public boolean addAdminEmployee(String name, String username, String pass){
      return employeeList.add(new Manager(name, username, pass));
  }

  public boolean addCashierEmployee(String name , String username, String pass){
    return employeeList.add(new Biller(name, username, pass));
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
    employeeList.add(new Manager("surya", "admin", BCrypt.hashpw("Admin@1", BCrypt.gensalt(10))));
    employeeList.add(new Biller("dhanush", "biller1", BCrypt.hashpw("Biller@1", BCrypt.gensalt(10))));
    employeeList.add(new Cashier("dhanush", "cashier1", BCrypt.hashpw("Cashier@1", BCrypt.gensalt(10))));
    employeeList.add(new FloorStaff("dhanush", "floorstaff1", BCrypt.hashpw("Floorstaff@1", BCrypt.gensalt(10))));
    employeeList.add(new Delivery("dhanush", "delivery1", BCrypt.hashpw("Delivery@1", BCrypt.gensalt(10))));

  }

  public boolean addFloorStaffEmployee(String name, String username, String hashpw) {
    return employeeList.add(new FloorStaff(name,username,hashpw));
  }

  public boolean addBiller(String name, String username, String hashpw) {
    return employeeList.add(new Biller(name, username, hashpw));
  }

  public boolean addDelivery(String name, String username, String hashpw) {
    return employeeList.add(new Delivery(name, username, hashpw));
  }
}

