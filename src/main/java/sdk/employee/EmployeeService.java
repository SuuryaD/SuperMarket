package sdk.employee;

import org.mindrot.jbcrypt.BCrypt;
import sdk.util.Globals;
import sdk.util.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  public EmployeeService() {
    employeeRepository = EmployeeRepository.getInstance();
  }
//TODO
  public boolean addEmployee(String employeeName, String username, String pass, int employeeType) throws ValidationException {
    if (isEmployeeAvailable(username))
      throw new ValidationException("Username already taken. Try with different username");

    if (employeeType == 1) {
      return employeeRepository.addAdminEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 2) {
      return employeeRepository.addCashierEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 3) {
      return employeeRepository.addFloorStaffEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else  if (employeeType == 4){
      return employeeRepository.addBillerEmployee(employeeName,username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if(employeeType == 5){
      return employeeRepository.addDeliveryEmployee(employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    }
    return false;
  }

  private boolean isEmployeeAvailable(String username) {
    Employee emp = employeeRepository.getEmployeeByUsername(username);
    return emp != null;
  }

  private boolean isEmployeeAvailable(int employeeId) {
    Employee emp = employeeRepository.getEmployeeById(employeeId);
    return emp != null;
  }

  public boolean removeEmployee(int employeeId) throws ValidationException {
    if (!isEmployeeAvailable(employeeId))
      throw new ValidationException("Employee employeeId does not match");
    return employeeRepository.removeEmployee(employeeId);
  }

  public Employee authenticate(String username, String pass) throws IllegalArgumentException {

    Employee emp = employeeRepository.getEmployeeByUsername(username);
    if (emp == null || !BCrypt.checkpw(pass, emp.getPassword())) {
      throw new IllegalArgumentException("Credentials incorrect");
    }
    Globals.currentEmployee = emp;
    return emp;
  }

  public String displayAllEmployees() {
    List<Employee> ls = employeeRepository.getAllEmployees();

    ArrayList<String> headers = new ArrayList<>();
    headers.add("Employee Id");
    headers.add("Employee Name");
    headers.add("Role");

    ArrayList<ArrayList<String>> content = new ArrayList<>();
    for (Employee emp : ls) {
      ArrayList<String> temp = new ArrayList<>();
      temp.add(String.valueOf(emp.getId()));
      temp.add(emp.getName());
      temp.add(emp.getRole());
//      if(emp instanceof Manager)
//        temp.add("Manager");
//      else if(emp instanceof Biller)
//        temp.add("Biller");
//      else if(emp instanceof Cashier)
//        temp.add("Cashier");
//      else if(emp instanceof FloorStaff)
//        temp.add("Floor Staff");
//      else if(emp instanceof Delivery)
//        temp.add("Delivery");
    content.add(temp);
    }
    return Globals.printTable(headers, content);
  }
}
