package sdk.employee.service;

import org.mindrot.jbcrypt.BCrypt;
import sdk.employee.domain.Employee;
import sdk.employee.repository.EmployeeLoginRepository;
import sdk.employee.repository.EmployeeRepository;
import sdk.util.Factory;
import sdk.util.Globals;
import sdk.util.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeLoginRepository employeeLoginRepository;
  public EmployeeServiceImpl() {
    employeeRepository = Factory.createEmployeeRepository();
    employeeLoginRepository = EmployeeLoginRepository.getInstance();
  }



  @Override
  public Boolean addEmployee(String employeeName, String username, String pass, int employeeType)
      throws ValidationException {
    if (isEmployeeAvailable(username))
      throw new ValidationException("Username already taken. Try with different username");

    if (employeeType == 1) {
      return employeeRepository.addManagerEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 2) {
      return employeeRepository.addCashierEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 3) {
      return employeeRepository.addFloorStaffEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 4) {
      return employeeRepository.addBillerEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 5) {
      return employeeRepository.addDeliveryEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
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

  @Override
  public boolean removeEmployee(int employeeId) throws ValidationException {
    if (!isEmployeeAvailable(employeeId))
      throw new ValidationException("Employee Id does not match");
    return employeeRepository.removeEmployee(employeeId);
  }


  @Override
  public Employee authenticate(String username, String pass) {
    int employeeId = employeeLoginRepository.authenticate(username, pass);
    if(employeeId == 0)
      throw new IllegalArgumentException();
    Employee emp = employeeRepository.getEmployeeById(employeeId);
    if(emp == null)
      throw new IllegalArgumentException();
    return emp;
  }

  @Override
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
