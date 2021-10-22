package sdk.employee;

import org.mindrot.jbcrypt.BCrypt;
import sdk.util.Globals;
import sdk.util.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  public EmployeeService() {
    employeeRepository = EmployeeRepository.getInstance();
  }

  public boolean addEmployee(String name, String username, String pass, int type) throws ValidationException {
    if (isUsernameAvailable(username))
      throw new ValidationException("Username already taken. Try with different username");

    if (type == 1) {
      return employeeRepository.addAdminEmployee(
          name, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (type == 2) {
      return employeeRepository.addCashierEmployee(
          name, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (type == 3) {
      return employeeRepository.addFloorStaffEmployee(
          name, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else  if (type == 4){
      return employeeRepository.addBiller(name,username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if(type == 5){
      return employeeRepository.addDelivery(name, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    }

    return false;
  }

  private boolean isUsernameAvailable(String username) {
    Employee emp = employeeRepository.getEmployeeByUsername(username);
    return emp != null;
  }

  private boolean isEmployeeAvailable(int id) {
    Employee emp = employeeRepository.getEmployeeById(id);
    return emp != null;
  }

  public boolean removeEmployee(int id) throws ValidationException {
    if (!isEmployeeAvailable(id))
      throw new ValidationException("Employee id does not match");
    return employeeRepository.removeEmployee(id);
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
      if(emp instanceof Manager)
        temp.add("Manager");
      else if(emp instanceof Biller)
        temp.add("Biller");
      else if(emp instanceof Cashier)
        temp.add("Cashier");
      else if(emp instanceof FloorStaff)
        temp.add("Floor Staff");
      else if(emp instanceof Delivery)
        temp.add("Delivery");
    content.add(temp);
    }
    return Globals.printTable(headers, content);
  }
}
