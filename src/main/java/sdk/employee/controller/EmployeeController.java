package sdk.employee.controller;

import sdk.employee.domain.Employee;
import sdk.util.ValidationException;

import java.util.List;

public interface EmployeeController {
  boolean addEmployee(String employeeName, String username, String pass, int employeeType)
      throws ValidationException;

  boolean removeEmployee(int employeeId) throws ValidationException;

  List<Employee> getAllEmployees();
}
