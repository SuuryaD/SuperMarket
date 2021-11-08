package sdk.employee.service;

import sdk.employee.domain.Employee;
import sdk.util.ValidationException;

import java.util.List;

public interface EmployeeService {

  Boolean addEmployee(String employeeName, String username, String pass, int employeeType);

  boolean removeEmployee(int employeeId);

  Employee authenticate(String username, String pass);

  List<Employee> getAllEmployees();

  boolean isEmployeeAvailable(String username);

  boolean isEmployeeAvailable(int employeeId);
}
