package sdk.employee.controller;

import sdk.employee.domain.Employee;
import sdk.employee.service.EmployeeService;
import sdk.util.Factory;
import sdk.util.ValidationException;

import java.util.List;

public class EmployeeControllerImpl implements EmployeeController {

  private final EmployeeService employeeService;
  private final EmployeeValidation employeeValidation;
  private final Employee emp;

  public EmployeeControllerImpl(Employee emp) {
    this.emp = emp;
    employeeService = Factory.createEmployeeService();
    employeeValidation = Factory.createEmployeeValidation();
  }

  @Override
  public boolean addEmployee(String employeeName, String username, String pass, int employeeType)
      throws ValidationException {

    if (employeeType > 5 || employeeType <= 0)
      throw new ValidationException("Enter a valid employeeType of employee.");

    employeeValidation.validateEmployeeName(employeeName);
    employeeValidation.validateEmployeeUsername(username);
    employeeValidation.validatePassword(pass);
    return employeeService.addEmployee(employeeName, username, pass, employeeType);
  }

  @Override
  public boolean removeEmployee(int employeeId) throws ValidationException {
    employeeValidation.validateEmployeeId(employeeId);
    return employeeService.removeEmployee(employeeId);
  }

  @Override
  public List<Employee> getAllEmployees() {
    return employeeService.getAllEmployees();
  }
}
