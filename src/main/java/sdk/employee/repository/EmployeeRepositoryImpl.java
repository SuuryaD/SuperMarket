package sdk.employee.repository;

import org.mindrot.jbcrypt.BCrypt;
import sdk.employee.domain.*;
import sdk.util.Factory;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

  private static EmployeeRepositoryImpl instance;

  public static EmployeeRepositoryImpl getInstance() {
    if (instance == null) instance = new EmployeeRepositoryImpl();
    return instance;
  }

  private final List<Employee> employeeList;
  private final EmployeeLoginRepository employeeLoginRepository;

  public EmployeeRepositoryImpl() {
    this.employeeList = new ArrayList<>();
    employeeLoginRepository = Factory.createEmployeeLoginRepository();
    initializeRepo();
  }

  @Override
  public Boolean addManagerEmployee(String employeeName, String username, String pass) {
    Employee emp = new Manager(employeeName, username);
    employeeList.add(emp);
    return employeeLoginRepository.addEmployee(emp.getId(), emp.getUsername(), pass);
  }

  @Override
  public Boolean addCashierEmployee(String employeeName, String username, String pass) {
    Employee emp = new Cashier(employeeName, username);
    employeeList.add(emp);
    return employeeLoginRepository.addEmployee(emp.getId(), username, pass);
  }

  @Override
  public Boolean addFloorStaffEmployee(String employeeName, String username, String pass) {
    Employee emp = new FloorStaff(employeeName, username);
    employeeList.add(emp);
    return employeeLoginRepository.addEmployee(emp.getId(), username, pass);
  }

  @Override
  public Boolean addBillerEmployee(String employeeName, String username, String pass) {
    Employee emp = new Biller(employeeName, username);
    employeeList.add(emp);
    return employeeLoginRepository.addEmployee(emp.getId(), username, pass);
  }

  @Override
  public Boolean addDeliveryEmployee(String employeeName, String username, String pass) {
    Employee emp = new Delivery(employeeName, username);
    employeeList.add(emp);
    return employeeLoginRepository.addEmployee(emp.getId(), username, pass);
  }

  @Override
  public boolean removeEmployee(int employeeId) {
    return employeeList.removeIf(employee -> employee.getId() == employeeId);
  }

  @Override
  public Employee getEmployeeByUsername(String username) {
    for (Employee emp : employeeList) {
      if (emp.getUsername().equals(username)) return emp;
    }
    return null;
  }

  @Override
  public Employee getEmployeeById(int employeeId) {
    for (Employee emp : employeeList) {
      if (emp.getId() == employeeId) return emp;
    }
    return null;
  }

  @Override
  public List<Employee> getAllEmployees() {
    return employeeList;
  }

  private void initializeRepo() {
    addManagerEmployee("surya", "manager1", BCrypt.hashpw("Manager@1", BCrypt.gensalt(10)));
    addBillerEmployee("surya", "biller1", BCrypt.hashpw("Biller@1", BCrypt.gensalt(10)));
    addDeliveryEmployee("surya", "delivery1", BCrypt.hashpw("Delivery@1", BCrypt.gensalt(10)));
    addCashierEmployee("surya", "cashier1", BCrypt.hashpw("Cashier@1", BCrypt.gensalt(10)));
    addFloorStaffEmployee(
        "surya", "floorstaff1", BCrypt.hashpw("Floorstaff@1", BCrypt.gensalt(10)));
  }
}
