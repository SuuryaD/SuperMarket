package sdk.employee;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

  private static EmployeeRepository instance;

  public static EmployeeRepository getInstance() {
    if (instance == null) instance = new EmployeeRepository();
    return instance;
  }

  private final List<Employee> employeeList;

  public EmployeeRepository() {
    this.employeeList = new ArrayList<>();
    initializeRepo();
  }

  public boolean addAdminEmployee(String employeeName, String username, String pass){
      return employeeList.add(new Manager(employeeName, username, pass));
  }

  public boolean addCashierEmployee(String employeeName , String username, String pass){
    return employeeList.add(new Biller(employeeName, username, pass));
  }

  public boolean addFloorStaffEmployee(String employeeName, String username, String hashpw) {
    return employeeList.add(new FloorStaff(employeeName,username,hashpw));
  }

  public boolean addBillerEmployee(String employeeName, String username, String hashpw) {
    return employeeList.add(new Biller(employeeName, username, hashpw));
  }

  public boolean addDeliveryEmployee(String employeeName, String username, String hashpw) {
    return employeeList.add(new Delivery(employeeName, username, hashpw));
  }

  public boolean removeEmployee(int employeeId) {
    return employeeList.removeIf(employee -> employee.getId() == employeeId);
  }

  public Employee getEmployeeByUsername(String username) {
    for (Employee emp : employeeList) {
      if (emp.getUsername().equals(username)) return emp;
    }
    return null;
  }

  public Employee getEmployeeById(int employeeId) {
    for (Employee emp : employeeList) {
      if (emp.getId() == employeeId) return emp;
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


}

