package sdk.employee.controller;

import sdk.employee.domain.Employee;
import sdk.employee.service.EmployeeServiceImpl;

/**
 * Contains all authentication functions
 */
public class Authenticate {

  public Employee login(String username, String pass) {
    return new EmployeeServiceImpl().authenticate(username, pass);
  }
}
