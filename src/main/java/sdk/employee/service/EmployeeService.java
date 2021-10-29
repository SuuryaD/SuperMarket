package sdk.employee.service;

import sdk.employee.domain.Employee;
import sdk.util.ValidationException;

public interface EmployeeService {

  Boolean addEmployee(String employeeName, String username, String pass, int employeeType)
      throws ValidationException;

  boolean removeEmployee(int employeeId) throws ValidationException;

  Employee authenticate(String username, String pass);

  String displayAllEmployees();
}
