package sdk.employee.service;

import org.mindrot.jbcrypt.BCrypt;
import sdk.employee.domain.Employee;
import sdk.employee.repository.EmployeeLoginRepository;
import sdk.employee.repository.EmployeeRepository;
import sdk.util.Factory;
import sdk.util.ValidationException;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeLoginRepository employeeLoginRepository;

  public EmployeeServiceImpl() {
    employeeLoginRepository = Factory.createEmployeeLoginRepository();
    employeeRepository = Factory.createEmployeeRepository();
  }

  @Override
  public Boolean addEmployee(String employeeName, String username, String pass, int employeeType)
  {

    if (employeeType == 1) {
      return employeeRepository.addManagerEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 2) {
      return employeeRepository.addBillerEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 3) {
      return employeeRepository.addFloorStaffEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 4) {
      return employeeRepository.addCashierEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    } else if (employeeType == 5) {
      return employeeRepository.addDeliveryEmployee(
          employeeName, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)));
    }
    return false;
  }

  public boolean isEmployeeAvailable(String username) {
    Employee emp = employeeRepository.getEmployeeByUsername(username);
    return emp != null;
  }

  public boolean isEmployeeAvailable(int employeeId) {
    Employee emp = employeeRepository.getEmployeeById(employeeId);
    return emp != null;
  }

  @Override
  public boolean removeEmployee(int employeeId) {
    return employeeRepository.removeEmployee(employeeId);
  }

  @Override
  public Employee authenticate(String username, String pass) {
    int employeeId = employeeLoginRepository.authenticate(username, pass);
    if (employeeId == 0) throw new IllegalArgumentException();
    Employee emp = employeeRepository.getEmployeeById(employeeId);
    if (emp == null) throw new IllegalArgumentException();
    return emp;
  }

  @Override
  public List<Employee> getAllEmployees() {
    return employeeRepository.getAllEmployees();
  }
}
